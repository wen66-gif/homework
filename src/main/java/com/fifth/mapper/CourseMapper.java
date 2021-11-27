package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fifth.domain.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
    //查找老师教的课程
    List<Course> findByTeacher(String no);
    //新增课程
    int saveCourse(String courseName);
    // 删除课程
    int deleteCourse(int id);

    /**
     * 查询学生课程
     * @param username 账号标记
     * @return
     */
    List<Course> stuCourseList(String username);

    /**
     * 查询教师课程
     * @param username 账号标记
     * @return
     */
    List<Course> teachCourseList(String username);
}
