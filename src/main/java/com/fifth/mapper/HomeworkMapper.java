package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fifth.domain.Homework;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:Homework的Dao
 * @Date 2021/11/26 00:00
 *****/
@Mapper
public interface HomeworkMapper extends BaseMapper<Homework> {
    /**
     * 查询 所学/所教 课程HomeWork
     * @param sno
     * @param courseId
     * @return
     */
    List<Homework> selectHomeWork(String sno, Integer courseId);

    // 教师端查询某一课程下所有作业
    List<Homework> allPublishHomework(int courseId);

    // 查询指定作业
    Homework oneHomework(int homeworkId);
}
