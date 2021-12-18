package com.fifth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fifth.domain.Classes;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface ClassesMapper extends BaseMapper<Classes> {
    // 查询所有课程
    List<Classes> findAllClasses();

    // 查询课程已经选上的班级
    List<Classes> findSelectedClasses(int id);

    // 查询课程未选上的班级
    List<Classes> findUnselectClasses(int id);

    // 查询未发布的班级
    List<Classes> findUnPublishClasses(int courseId, int homeworkId);

    // 查询一个班级
    Classes findOneClass(String search);
}
