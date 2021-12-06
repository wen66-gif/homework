package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fifth.domain.Choice;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:Choice的Dao
 * @Date 2021/11/26 00:00
 *****/
@Mapper
public interface ChoiceMapper extends BaseMapper<Choice> {
    int saveChoice(List<Choice> choiceList);
}
