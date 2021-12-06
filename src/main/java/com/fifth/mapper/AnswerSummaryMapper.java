package com.fifth.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fifth.domain.AnswerSummary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:AnswerSummary的Dao
 * @Date 2021/11/26 00:00
 *****/
@Mapper
public interface AnswerSummaryMapper extends BaseMapper<AnswerSummary> {
    // 查询某一作业 已交学生
//    List<AnswerSummary> findSubmitList(int homeworkId);
    IPage<AnswerSummary> findSubmitList(Page<?> page, int homeworkId, List<String> classList);

    // 查询某一作业 未交学生
    IPage<AnswerSummary> findUnSubmitList(Page<?> page, int homeworkId, List<String> classList);
}
