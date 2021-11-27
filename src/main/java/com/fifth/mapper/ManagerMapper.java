package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fifth.domain.Manager;
import org.apache.ibatis.annotations.Mapper;

/****
 * @Author:Anonym
 * @Description:Manager的Dao
 * @Date 2021/11/26 00:00
 *****/
@Mapper
public interface ManagerMapper extends BaseMapper<Manager> {
}
