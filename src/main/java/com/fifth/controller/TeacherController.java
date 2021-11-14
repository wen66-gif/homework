package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fifth.common.Result;
import com.fifth.domain.Course;
import com.fifth.domain.Open;
import com.fifth.domain.Teach;
import com.fifth.mapper.CourseMapper;
import com.fifth.mapper.OpenMapper;
import com.fifth.mapper.TeachMapper;
import com.fifth.mapper.TeacherMapper;
import com.fifth.utils.CurrentUser;
import com.fifth.utils.TokenUtil;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class TeacherController {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    OpenMapper openMapper;
    @Autowired
    TeachMapper teachMapper;

    // 登录
    @PostMapping("/login")
    public Result login(@RequestParam String no, @RequestParam String password){
        System.out.println("进入teacherController");
        if (teacherMapper.login(no,password)>0){
            // 登录成功，（用户名及账号）生成token返回给前端
            return Result.success(TokenUtil.createJwtToken(no,getUserName(no)));
        }
        else
            return Result.error("-1","用户名或密码错误");
    }

    // 获取当前教师全部课程
    @GetMapping("/mycourse")
    public Result findMyCourse(){
        System.out.println("进入teacherController\n"+"userId:"+CurrentUser.getCurrentUserId());
        List<Course> courses = courseMapper.findByTeacher(CurrentUser.getCurrentUserId());
        Map<String,Object> map = new HashMap<>();
        map.put("courses",courses);
        map.put("userName",CurrentUser.getCurrentUserName());
        return Result.success(map);
    }

    //新增课程
    @PostMapping("/mycourse")
    public Result saveClass(@RequestParam String courseName,
                            @RequestParam(value = "selectClass[]") int selectClass[]) {
        System.out.println("进入Controller");
        if (courseMapper.saveCourse(courseName)>0){
            // 查询新建课程id,(mybatis-plus)
            QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("name",courseName);
            Course course = courseMapper.selectOne(queryWrapper);
            // 将课程-班级关系数据保存进List
            List<Open> openList = new ArrayList<>();
            for (int classId : selectClass) {
                Open open = new Open();
                open.setClassId(classId);
                open.setCourseId(course.getId());
                openList.add(open);
            }
            //向关系表插入数据
            if (teachMapper.saveTeach(CurrentUser.getCurrentUserId(),course.getId())>0&&openMapper.saveOpen(openList)>0) {
                return Result.success();
            }
            System.out.println("新增成功");
            return Result.error("-1","操作失败");
        }
        else{
            return Result.error("-1","操作失败");
        }
    }

    // 删除课程
    @DeleteMapping("/mycourse")
    public Result deleteCourse(@PathParam(value = "id") int id){
        System.out.println("进入delete");
        if (courseMapper.deleteCourse(id)>0) {
            System.out.println("成功");
            return Result.success();
        }

        else
        {
            System.out.println("失败");
            return Result.error("-1","删除失败");
        }
    }

    // 获取登录用户名
    public String getUserName(String no) {
        return teacherMapper.getUserName(no);
    }

}
