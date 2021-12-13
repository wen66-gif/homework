package com.fifth.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fifth.common.Result;
import com.fifth.domain.Manager;
import com.fifth.domain.Student;
import com.fifth.domain.Teacher;
import com.fifth.mapper.ManagerMapper;
import com.fifth.mapper.StudentMapper;
import com.fifth.mapper.TeacherMapper;
import com.fifth.utils.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class UpdatePasswordController {
    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private StudentMapper studentMapper;

    @Autowired
    private ManagerMapper managerMapper;

    @PutMapping("/updatePassword")
    public Result updatePassword(@RequestBody JSONObject jsonObject){
        String oldPassword = jsonObject.getString("oldPassword");
        String newPassword = jsonObject.getString("newPassword");
        switch (CurrentUser.getCurrentUserRole()){
            case 1:
                Student student = studentMapper.selectOne(new QueryWrapper<Student>().eq("no", CurrentUser.getCurrentUserId()));
                if (oldPassword.equals(student.getPassword()))
                {
                    student.setPassword(newPassword);
                    studentMapper.updateById(student);
                    return Result.success();
                }
                else {
                    return Result.error("-1","原密码输入错误");
                }
            case 2:
                Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("no", CurrentUser.getCurrentUserId()));
                if (oldPassword.equals(teacher.getPassword()))
                {
                    teacher.setPassword(newPassword);
                    teacherMapper.updateById(teacher);
                    return Result.success();
                }
                else {
                    return Result.error("-1","原密码输入错误");
                }
            case 3:
                Manager manager = managerMapper.selectOne(new QueryWrapper<Manager>().eq("id", CurrentUser.getCurrentUserId()));
                if (oldPassword.equals(manager.getPassword()))
                {
                    manager.setPassword(newPassword);
                    managerMapper.updateById(manager);
                    return Result.success();
                }
                else {
                    return Result.error("-1","原密码输入错误");
                }
        }
        return Result.error("-2","出错啦");
    }
}
