package com.fifth.mapper;

import com.fifth.domain.Classes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ClassesMapper {
    // 查询所有课程
    List<Classes> findAllClasses();
}
