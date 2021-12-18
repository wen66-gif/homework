package com.fifth.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fifth.common.Result;
import com.fifth.domain.Classes;
import com.fifth.domain.Teacher;
import com.fifth.mapper.ClassesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ClassesController {
    @Autowired
    ClassesMapper classesMapper;

    @GetMapping("/allClasses")
    public Result findAllClasses(){
        if (classesMapper.findAllClasses() != null){
            return Result.success(classesMapper.findAllClasses());
        }
        else {
            return Result.error("-1","查询班级失败");
        }
    }

    // 查询课程已经选上的班级
    @GetMapping("/selectedClasses")
    public Result findSelectedClasses(@RequestParam(value = "courseId") int id){
        if (classesMapper.findSelectedClasses(id) != null) {
            System.out.println("已选择班级："+classesMapper.findSelectedClasses(id));
            return Result.success(classesMapper.findSelectedClasses(id));
        }
        else {
            return Result.error("-1","查询课程失败");
        }
    }

    // 查询课程未选择的班级
    @GetMapping("/unselectClasses")
    public Result findUnselectedClasses(@RequestParam(value = "courseId") int id){
        if (classesMapper.findUnselectClasses(id) != null) {
            return Result.success(classesMapper.findUnselectClasses(id));
        }
        else {
            return Result.error("-1","查询课程失败");
        }
    }

    @GetMapping("/unPublishClasses")
    // 发布作业可选班级，不包含已发布的班级
    public Result findUnPublishClasses(@RequestParam int courseId, @RequestParam int homeworkId){
        List<Classes> classesList = classesMapper.findUnPublishClasses(courseId,homeworkId);
        return Result.success(classesList);
    }

    // 获取全部班级信息
    @GetMapping("/getAllClasses")
    public Result getAllTeachers(@RequestParam int pageNum,
                                 @RequestParam int pageSize){
        Page<Classes> page = new Page<>(pageNum, pageSize);

        Page<Classes> classesPage = classesMapper.selectPage(page, null);
        return Result.success(classesPage);
    }
    // 新增一个班级
    @PostMapping("/saveClass")
    public Result saveClass(@RequestBody Classes classes){
        try {
            if (classesMapper.insert(classes) > 0)
                return Result.success();
            else
                return Result.error("-1","新增班级失败");
        }catch (Exception e){
            return Result.error("-2","名称已存在");
        }
    }

    // 查询一个班级
    @GetMapping("/findOneClass")
    public Result findOneClass(@RequestParam String search){
        Classes classes = classesMapper.findOneClass(search);
        return Result.success(classes);
    }

    // 更新班级信息
    @PutMapping("/updateClass")
    public Result updateClass(@RequestBody Classes classes){
        try {
                if (classesMapper.updateById(classes) > 0){
                    return Result.success();
                }
                else
                    return Result.error("-1","更新失败");
        }catch (Exception e){
            return Result.error("-2","名称已存在");
        }
    }

    // 删除班级
    @DeleteMapping("/deleteClass")
    public Result deleteClass(@RequestParam int id){
        if (classesMapper.delete(new QueryWrapper<Classes>().eq("id",id)) > 0){
            return Result.success();
        }
        else
            return Result.error("-1","删除失败");
    }

}
