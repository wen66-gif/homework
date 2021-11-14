package com.fifth.controller;

import com.fifth.common.Result;
import com.fifth.domain.Classes;
import com.fifth.mapper.ClassesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
