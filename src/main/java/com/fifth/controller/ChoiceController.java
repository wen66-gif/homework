package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fifth.common.Result;
import com.fifth.domain.Choice;
import com.fifth.mapper.ChoiceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/choice")
@CrossOrigin
public class ChoiceController {

    @Autowired
    private ChoiceMapper choiceMapper;

    /***
     * 根据ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用choiceMapper实现根据主键删除
        if (choiceMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "删除失败");
    }

    /***
     * 修改Choice数据
     * @param choice
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Choice choice, @PathVariable Integer id) {
        //设置主键值
        choice.setId(id);
        //调用choiceMapper实现修改Choice
        if (choiceMapper.update(choice, new UpdateWrapper<Choice>().eq("id",id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增Choice数据
     * @param choice
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Choice choice) {
        //调用choiceMapper实现添加Choice
        if (choiceMapper.insert(choice) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询Choice数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Choice> findById(@PathVariable Integer id) {
        //调用choiceMapper实现根据主键查询Choice
        Choice choice = choiceMapper.selectById(id);
        return Result.success(choice);
    }

    /***
     * 查询Choice全部数据
     * @return
     */
    @GetMapping
    public Result<List<Choice>> findAll() {
        //调用choiceMapper实现查询所有Choice
        List<Choice> list = choiceMapper.selectList(null);
        return Result.success(list);
    }
}
