package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fifth.common.Result;
import com.fifth.domain.Teach;
import com.fifth.mapper.TeachMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/teach")
@CrossOrigin
public class TeachController {

    @Autowired
    private TeachMapper teachMapper;

    /***
     * 根据ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用teachMapper实现根据主键删除
        if (teachMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "删除失败");
    }

    /***
     * 修改Teach数据
     * @param teach
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Teach teach, @PathVariable Integer id) {
        //设置主键值
        teach.setId(id);
        //调用teachMapper实现修改Teach
        if (teachMapper.update(teach, new UpdateWrapper<Teach>().eq("id",id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增Teach数据
     * @param teach
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Teach teach) {
        //调用teachMapper实现添加Teach
        if (teachMapper.insert(teach) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询Teach数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Teach> findById(@PathVariable Integer id) {
        //调用teachMapper实现根据主键查询Teach
        Teach teach = teachMapper.selectById(id);
        return Result.success(teach);
    }

    /***
     * 查询Teach全部数据
     * @return
     */
    @GetMapping
    public Result<List<Teach>> findAll() {
        //调用teachMapper实现查询所有Teach
        List<Teach> list = teachMapper.selectList(null);
        return Result.success(list);
    }
}
