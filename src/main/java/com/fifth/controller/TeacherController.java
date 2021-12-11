package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fifth.common.Result;
import com.fifth.domain.*;
import com.fifth.mapper.*;
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
    @Autowired
    ClassHomeworkMapper classHomeworkMapper;
    @Autowired
    HomeworkMapper homeworkMapper;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    AnswerSummaryMapper answerSummaryMapper;
    @Autowired
    StudentAnswerMapper studentAnswerMapper;
    // 获取登录用户名
    public String getUserName(String no) {
        return teacherMapper.getUserName(no);
    }

    // 登录
    @PostMapping("/login")
    public Result login(@RequestParam String no, @RequestParam String password){
//        System.out.println("进入teacherController");
        if (teacherMapper.login(no,password)>0){
            // 登录成功，（用户名及账号）生成token返回给前端
            return Result.success(TokenUtil.createJwtToken(no,getUserName(no)));
        }
        else
            return Result.error("-1","用户名或密码错误");
    }

    // 获取当前教师全部课程
//    @GetMapping("/mycourse")
//    public Result findMyCourse(){
//        System.out.println("进入teacherController\n"+"userId:"+CurrentUser.getCurrentUserId());
//        List<Course> courses = courseMapper.findByTeacher(CurrentUser.getCurrentUserId());
//        Map<String,Object> map = new HashMap<>();
//        map.put("courses",courses);
//        map.put("userName",CurrentUser.getCurrentUserName());
//        return Result.success(map);
//    }

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

    // 删除已经选择一个班级
    @DeleteMapping("/delete_class")
    public Result deleteClassById(@RequestParam int classId,@RequestParam int courseId){
        QueryWrapper<Open> wrapper=new QueryWrapper<>();
        wrapper
                .eq("class_id",classId)
                .eq("course_id",courseId);
        if (openMapper.delete(wrapper) > 0){
            // 删除班级后，还要删除与这个班级有关的作业
            // 查看该课程所有作业id（在作业表中查询） => 从class_homework表删除（classId,homeworkId)
            QueryWrapper<Homework> w=new QueryWrapper<>();
            w.eq("course_id",courseId);
            List<Homework> homeworkList = homeworkMapper.selectList(w);
            for (int i = 0; i < homeworkList.size(); i++) {
                classHomeworkMapper.delete(new QueryWrapper<ClassHomework>().eq("class_id",classId)
                        .eq("homework_id",homeworkList.get(i).getId()));
                //删除answer_summary  学生是被删除班级 && 作业属于这门课
                // 查看该班级所有学生 => 该课程所有作业 => 从answer_summary删除（student_no, homeworkId）
                QueryWrapper<Student> s = new QueryWrapper<>();
                s.eq("class_id",classId);
                List<Student> studentList = studentMapper.selectList(s);
                for (int j = 0; j < studentList.size(); j++) {
                    answerSummaryMapper.delete(new QueryWrapper<AnswerSummary>().eq("student_no",studentList.get(j).getNo())
                            .eq("homework_id",homeworkList.get(i).getId()));
                }
            }
            //删除student_answer 答案 作答学生是被删除班级 && 作业属于这门课
            return Result.success();
        }
        else {
            return Result.error("-1","删除失败");
        }
    }

    // 删除一个作业，不涉及作业库
    @DeleteMapping("/delete_homework_record")
    public Result deleteRecord(@RequestParam int homeworkId){
        QueryWrapper<ClassHomework> c = new QueryWrapper<ClassHomework>().eq("homework_id",homeworkId);
        // 从class_homework删除作业id为该被删除作业的记录
        if (classHomeworkMapper.delete(c) > 0) {
            // //删除answer_summary  学生有作答这个作业的记录
            // 从answer_summary删除（homeworkId）
            QueryWrapper<AnswerSummary> a = new QueryWrapper<>();
            a.eq("homework_id",homeworkId);
            answerSummaryMapper.delete(a);
            //删除学生作答具体题目的记录
            studentAnswerMapper.delete(new QueryWrapper<StudentAnswer>().eq("homework_id",homeworkId));
            return Result.success();
        }
        else {
            return Result.error("-1","删除失败");
        }
    }
    // 导入班级
    @PostMapping("/import_class")
    public Result importClass(@RequestParam(value = "selectClass[]") int selectClass[], @RequestParam int courseId){
        // 将课程-班级关系数据保存进List
        List<Open> openList = new ArrayList<>();
        for (int classId : selectClass) {
            Open open = new Open();
            open.setClassId(classId);
            open.setCourseId(courseId);
            openList.add(open);
        }
        if (openMapper.saveOpen(openList) > 0){
            return Result.success();
        }
        else {
            return Result.error("-1","操作失败");
        }
    }


}
