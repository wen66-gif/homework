package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fifth.common.Result;
import com.fifth.domain.Homework;
import com.fifth.mapper.HomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/homework")
@CrossOrigin
public class HomeworkController {

    @Autowired
    private HomeworkMapper homeworkMapper;

    /**
     * 查询 所学/所教 课程HomeWork
     *
     * @param sno      用户标记
     * @param courseId 课程号
     * @return
     */
    @GetMapping("/{sno}/{courseId}")
    public Result selectWorkList(@PathVariable String sno,
                                 @PathVariable Integer courseId) {
        List<Homework> workList = homeworkMapper.selectHomeWork(sno, courseId);
        return Result.success(workList);
    }

    /***
     * 根据ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用homeworkMapper实现根据主键删除
        if (homeworkMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "删除失败");
    }

    /***
     * 修改Homework数据
     * @param homework
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Homework homework, @PathVariable Integer id) {
        //设置主键值
        homework.setId(id);
        //调用homeworkMapper实现修改Homework
        if (homeworkMapper.update(homework, new UpdateWrapper<Homework>().eq("id",id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增Homework数据
     * @param homework
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Homework homework) {
        //调用homeworkMapper实现添加Homework
        if (homeworkMapper.insert(homework) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询Homework数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Homework> findById(@PathVariable Integer id) {
        //调用homeworkMapper实现根据主键查询Homework
        Homework homework = homeworkMapper.selectById(id);
        return Result.success(homework);
    }

    /***
     * 查询Homework全部数据
     * @return
     */
    @GetMapping
    public Result<List<Homework>> findAll() {
        //调用homeworkMapper实现查询所有Homework
        List<Homework> list = homeworkMapper.selectList(null);
        return Result.success(list);
    }
}
