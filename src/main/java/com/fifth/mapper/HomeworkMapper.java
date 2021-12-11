package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fifth.domain.AnswerSummary;
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
     * @param no
     * @param courseId
     * @return
     */
    IPage<Homework> selectHomeWork(Page<?> page, String no, Integer courseId);

    // 教师端查询某一课程下所有作业
    List<Homework> allPublishHomework(int courseId);

    // 查询指定作业
    Homework oneHomework(int homeworkId);
}
