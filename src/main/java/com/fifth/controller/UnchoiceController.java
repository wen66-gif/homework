package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fifth.common.Result;
import com.fifth.domain.Unchoice;
import com.fifth.mapper.UnchoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/unchoice")
@CrossOrigin
public class UnchoiceController {

    @Autowired
    private UnchoiceMapper unchoiceMapper;

    /***
     * 根据ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用unchoiceMapper实现根据主键删除
        if (unchoiceMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "删除失败");
    }

    /***
     * 修改Unchoice数据
     * @param unchoice
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Unchoice unchoice, @PathVariable Integer id) {
        //设置主键值
        unchoice.setId(id);
        //调用unchoiceMapper实现修改Unchoice
        if (unchoiceMapper.update(unchoice, new UpdateWrapper<Unchoice>().eq("id",id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增Unchoice数据
     * @param unchoice
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Unchoice unchoice) {
        //调用unchoiceMapper实现添加Unchoice
        if (unchoiceMapper.insert(unchoice) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询Unchoice数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Unchoice> findById(@PathVariable Integer id) {
        //调用unchoiceMapper实现根据主键查询Unchoice
        Unchoice unchoice = unchoiceMapper.selectById(id);
        return Result.success(unchoice);
    }

    /***
     * 查询Unchoice全部数据
     * @return
     */
    @GetMapping
    public Result<List<Unchoice>> findAll() {
        //调用unchoiceMapper实现查询所有Unchoice
        List<Unchoice> list = unchoiceMapper.selectList(null);
        return Result.success(list);
    }
}
