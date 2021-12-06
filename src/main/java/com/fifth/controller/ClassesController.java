package com.fifth.controller;

import com.fifth.common.Result;
import com.fifth.domain.Classes;
import com.fifth.mapper.ClassesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            return Result.error("-1","查询课程失败");
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

}
