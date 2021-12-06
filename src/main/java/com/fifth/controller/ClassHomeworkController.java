package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fifth.common.Result;
import com.fifth.domain.ClassHomework;
import com.fifth.domain.Classes;
import com.fifth.mapper.ClassHomeworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/classHomework")
@CrossOrigin
public class ClassHomeworkController {

    @Autowired
    private ClassHomeworkMapper classHomeworkMapper;

    @PostMapping("/saveRecord")
    public Result saveRecord(@RequestParam(value = "classes[]") int classId[], @RequestParam int homeworkId){
        try {
            for (int c : classId) {
                ClassHomework classHomework = new ClassHomework();
                classHomework.setClassId(c);
                classHomework.setHomeworkId(homeworkId);
                classHomework.setPublishTime(new Date());
                classHomeworkMapper.insert(classHomework);

            }
        }catch (Exception e){
            return Result.error("-1","发布失败");
        }
        return Result.success();
    }

    /***
     * 根据ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用classHomeworkMapper实现根据主键删除
        if (classHomeworkMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 修改classHomework数据
     * @param classHomework
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody ClassHomework classHomework, @PathVariable Integer id) {
        //设置主键值
        classHomework.setId(id);
        //调用classHomeworkMapper实现修改classHomework
        if (classHomeworkMapper.update(classHomework, new UpdateWrapper<ClassHomework>().eq("id",id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增classHomework数据
     * @param classHomework
     * @return
     */
    @PostMapping
    public Result add(@RequestBody ClassHomework classHomework) {
        //调用classHomeworkMapper实现添加classHomework
        if (classHomeworkMapper.insert(classHomework) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询classHomework数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<ClassHomework> findById(@PathVariable Integer id) {
        //调用classHomeworkMapper实现根据主键查询classHomework
        ClassHomework classHomework = classHomeworkMapper.selectById(id);
        return Result.success(classHomework);
    }

    /***
     * 查询classHomework全部数据
     * @return
     */
    @GetMapping
    public Result<List<ClassHomework>> findAll() {
        //调用classHomeworkMapper实现查询所有classHomework
        List<ClassHomework> list = classHomeworkMapper.selectList(null);
        return Result.success(list);
    }
}
