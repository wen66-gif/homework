package com.fifth.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fifth.domain.Open;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OpenMapper extends BaseMapper<Open> {
    int saveOpen(List<Open> openList);
}
