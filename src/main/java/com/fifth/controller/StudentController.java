package com.fifth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fifth.common.Result;
import com.fifth.domain.Student;
import com.fifth.domain.Teacher;
import com.fifth.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;

    // 获取全部学生信息
    @GetMapping("/getAllStudents")
    public Result getAllStudents(@RequestParam int pageNum,
                                 @RequestParam int pageSize,
                                 @RequestParam(required = false) String sortField,
                                 @RequestParam(required = false) String sortOrder){
        Page<Student> page = new Page<>(pageNum, pageSize);
        OrderItem orderItem = new OrderItem();
        if (sortField!=null){
            orderItem.setColumn(sortField);
        }
        if (sortOrder!=null){
            if (sortOrder.equals("ascend")){
                orderItem.setAsc(true);
            }
            else {
                orderItem.setAsc(false);
            }
        }
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        page.setOrders(orderItems);
        IPage<Student> studentPage = studentMapper.getAllStudents(page);
        return Result.success(studentPage);
    }
    // 新增一个学生
    @PostMapping("/saveStudent")
    public Result saveStudent(@RequestBody Student student){
        try {
            if (studentMapper.insert(student) > 0)
                return Result.success();
            else
                return Result.error("-1","新增学生失败");
        }catch (Exception e){
            return Result.error("-2","学号已存在");
        }

    }

    // 查询一个学生
    @GetMapping("/findStudentByKey")
    public Result findStudentByKey(@RequestParam String search){
        List<Student> student = studentMapper.findStudentByKey(search);
        return Result.success(student);
    }

    // 更新学生信息
    @PutMapping("/updateStudent")
    public Result updateStudent(@RequestBody JSONObject jsonObject) {
        try {
            Student oldStudent = JSON.parseObject(JSON.toJSONString(jsonObject.get("oldValue")), Student.class);
            Student newStudent = JSON.parseObject(JSON.toJSONString(jsonObject.get("newValue")), Student.class);
            Map paramMap = new HashMap();
            paramMap.put("no", newStudent.getNo());
            paramMap.put("name", newStudent.getName());
            paramMap.put("sex", newStudent.getSex());
            paramMap.put("password", newStudent.getPassword());
            paramMap.put("classId", newStudent.getClassId());
            paramMap.put("oldNo", oldStudent.getNo());
            if (studentMapper.updateStudent(paramMap) > 0) {
                return Result.success();
            }
            else return Result.error("-1","更新失败");
        } catch (Exception e) {
            return Result.error("-2", "学号已存在");
        }
    }
    // 删除学生
    @DeleteMapping("/deleteStudent")
    public Result deleteStudent(@RequestParam String no){
        if (studentMapper.delete(new QueryWrapper<Student>().eq("no",no)) > 0){
            return Result.success();
        }
        else
            return Result.error("-1","删除失败");
    }

    @GetMapping("/getStudent")
    public Result<Student> findById(@RequestParam String studentNo) {
        Student student = studentMapper.getStudent(studentNo);
        return Result.success(student);
    }
}
