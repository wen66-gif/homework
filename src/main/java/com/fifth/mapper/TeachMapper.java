package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fifth.domain.Teach;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TeachMapper extends BaseMapper<Teach> {
    int saveTeach(String tno,int courseId);
}
