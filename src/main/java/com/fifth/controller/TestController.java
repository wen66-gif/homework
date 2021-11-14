package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fifth.common.Result;
import com.fifth.domain.Teacher;
import com.fifth.mapper.CourseMapper;
import com.fifth.mapper.TeachMapper;
import com.fifth.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
public class TestController {

    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @PostMapping("/test")
    public Result saveClass(@RequestParam String courseName,
                            @RequestParam(value = "selectClass[]") String[] selectClass) {
        System.out.println("进入Controller");
        System.out.println(selectClass);
        if (courseMapper.saveCourse(courseName)!=0){
            System.out.println("新增成功");

            return Result.success();
        }
        else{
            return Result.error("-1","操作失败");
        }
    }



}
