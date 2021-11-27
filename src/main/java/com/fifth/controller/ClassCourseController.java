package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fifth.common.Result;
import com.fifth.domain.ClassCourse;
import com.fifth.mapper.ClassCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/classCourse")
@CrossOrigin
public class ClassCourseController {

    @Autowired
    private ClassCourseMapper classCourseMapper;

    /***
     * 根据ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用classCourseMapper实现根据主键删除
        if (classCourseMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 修改ClassCourse数据
     * @param classCourse
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody ClassCourse classCourse, @PathVariable Integer id) {
        //设置主键值
        classCourse.setId(id);
        //调用classCourseMapper实现修改ClassCourse
        if (classCourseMapper.update(classCourse, new UpdateWrapper<ClassCourse>().eq("id",id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增ClassCourse数据
     * @param classCourse
     * @return
     */
    @PostMapping
    public Result add(@RequestBody ClassCourse classCourse) {
        //调用classCourseMapper实现添加ClassCourse
        if (classCourseMapper.insert(classCourse) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询ClassCourse数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ClassCourse> findById(@PathVariable Integer id) {
        //调用classCourseMapper实现根据主键查询ClassCourse
        ClassCourse classCourse = classCourseMapper.selectById(id);
        return Result.success(classCourse);
    }

    /***
     * 查询ClassCourse全部数据
     * @return
     */
    @GetMapping
    public Result<List<ClassCourse>> findAll() {
        //调用classCourseMapper实现查询所有ClassCourse
        List<ClassCourse> list = classCourseMapper.selectList(null);
        return Result.success(list);
    }
}
