package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fifth.common.Result;
import com.fifth.domain.Open;
import com.fifth.mapper.OpenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/open")
@CrossOrigin
public class OpenController {

    @Autowired
    private OpenMapper openMapper;

    /***
     * 根据ID删除品牌数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用openMapper实现根据主键删除
        if (openMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "删除失败");
    }

    /***
     * 修改Open数据
     * @param open
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Open open, @PathVariable Integer id) {
        //设置主键值
        open.setId(id);
        //调用openMapper实现修改Open
        if (openMapper.update(open, new UpdateWrapper<Open>().eq("id",id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增Open数据
     * @param open
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Open open) {
        //调用openMapper实现添加Open
        if (openMapper.insert(open) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询Open数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Open> findById(@PathVariable Integer id) {
        //调用openMapper实现根据主键查询Open
        Open open = openMapper.selectById(id);
        return Result.success(open);
    }

    /***
     * 查询Open全部数据
     * @return
     */
    @GetMapping
    public Result<List<Open>> findAll() {
        //调用openMapper实现查询所有Open
        List<Open> list = openMapper.selectList(null);
        return Result.success(list);
    }
}
