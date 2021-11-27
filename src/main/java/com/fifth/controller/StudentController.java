package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fifth.common.Result;
import com.fifth.domain.Student;
import com.fifth.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/student")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentMapper studentMapper;

    /**
     * 分页查询
     * @return
     */
    @GetMapping("/findPage")
    public Result selectAll() {
        //调用studentMapper实现根据主键删除
        Page<Student> studentPage = studentMapper.selectPage(new Page<Student>(), null);
        return Result.success(studentPage);
    }

    /***
     * 根据ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable String id) {
        //调用studentMapper实现根据主键删除
        if (studentMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "删除失败");
    }

    /***
     * 修改Student数据
     * @param student
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Student student, @PathVariable String id) {
        //设置主键值
        student.setNo(id);
        //调用studentMapper实现修改Student
        if (studentMapper.update(student, new UpdateWrapper<Student>().eq("no", id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增Student数据
     * @param student
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Student student) {
        //调用studentMapper实现添加Student
        if (studentMapper.insert(student) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询Student数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Student> findById(@PathVariable String id) {
        //调用studentMapper实现根据主键查询Student
        Student student = studentMapper.selectById(id);
        return Result.success(student);
    }

    /***
     * 查询Student全部数据
     * @return
     */
    @GetMapping
    public Result<List<Student>> findAll() {
        //调用studentMapper实现查询所有Student
        List<Student> list = studentMapper.selectList(null);
        return Result.success(list);
    }
}
