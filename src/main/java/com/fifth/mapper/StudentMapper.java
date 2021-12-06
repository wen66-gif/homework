package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fifth.domain.Student;
import org.apache.ibatis.annotations.Mapper;

/****
 * @Author:Anonym
 * @Description:Student的Dao
 * @Date 2021/11/26 00:00
 *****/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    Student getStudent(String no);
}
