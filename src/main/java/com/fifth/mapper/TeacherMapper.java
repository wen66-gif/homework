package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fifth.domain.Classes;
import com.fifth.domain.Teach;
import com.fifth.domain.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TeacherMapper extends BaseMapper<Teacher> {
    //登录
    int login(String no, String password);

    //获取账户名
    String getUserName(String no);

    // 查询一个教师
    List<Teacher> findTeacherByKey(String search);

    // 更新教师信息
    int updateTeacher(Map paramMap);
}
