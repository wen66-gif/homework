package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fifth.common.Result;
import com.fifth.common.UserInfo;
import com.fifth.domain.Manager;
import com.fifth.domain.Student;
import com.fifth.domain.Teacher;
import com.fifth.mapper.ManagerMapper;
import com.fifth.mapper.StudentMapper;
import com.fifth.mapper.TeacherMapper;
import com.fifth.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/***
 * @author: Anonym
 * @data: 2021/11/25 12:06
 **/
@RestController
@RequestMapping("/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private ManagerMapper managerMapper;

    /**
     * 用户登录
     *
     * @param role     角色
     * @param userInfo 登录信息
     * @return
     */
    @PostMapping("/{role}")
    public Result login(@PathVariable("role") Integer role,
                        @RequestBody UserInfo userInfo) {
        String userName = userInfo.getUserName();
        String password = userInfo.getPassword();

        switch (role) {
            case 1:
                Student student = studentMapper.selectOne(new QueryWrapper<>(
                        new Student(userName, null, password)));
                if (!StringUtils.isEmpty(student))
                    return Result.success(TokenUtil.createJwtToken(student.getNo(), student.getName()));
                break;
            case 2:
                Teacher teacher = teacherMapper.selectOne(new QueryWrapper<Teacher>().eq("no",userName).eq("password",password));
                if (!StringUtils.isEmpty(teacher))
                    return Result.success(TokenUtil.createJwtToken(teacher.getNo(), teacher.getName()));
                break;
            case 3:
                Manager manager = managerMapper.selectOne(new QueryWrapper<>(
                        new Manager(userName, password)));
                if (!StringUtils.isEmpty(manager))
                    return Result.success(TokenUtil.createJwtToken(String.valueOf(manager.getId()), manager.getAccount()));
                break;
            default:
                return Result.error("-1", "出错啦！");
        }
        return Result.error("-1", "用户名或密码错误");
    }
}
