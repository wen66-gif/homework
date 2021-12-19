package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fifth.domain.Homework;
import com.fifth.domain.Student;
import com.fifth.domain.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/****
 * @Author:Anonym
 * @Description:Student的Dao
 * @Date 2021/11/26 00:00
 *****/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    IPage<Student> getAllStudents(Page<?> page);

    // 查询一个学生
    List<Student> findStudentByKey(String search);

    // 更新学生信息
    int updateStudent(Map paramMap);
}
