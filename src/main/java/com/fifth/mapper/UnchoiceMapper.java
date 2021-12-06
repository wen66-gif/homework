package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fifth.domain.Unchoice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:Unchoice的Dao
 * @Date 2021/11/26 00:00
 *****/
@Mapper
public interface UnchoiceMapper extends BaseMapper<Unchoice> {
    int saveUnChoice(List<Unchoice> unChoiceList);
}
