package com.fifth.controller;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fifth.common.Result;
import com.fifth.domain.Manager;
import com.fifth.mapper.ManagerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/manager")
@CrossOrigin
public class ManagerController {

    @Autowired
    private ManagerMapper managerMapper;

    /***
     * 根据ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用managerMapper实现根据主键删除
        if (managerMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "删除失败");
    }

    /***
     * 修改Manager数据
     * @param manager
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody Manager manager, @PathVariable Integer id) {
        //设置主键值
        manager.setId(id);
        //调用managerMapper实现修改Manager
        if (managerMapper.update(manager, new UpdateWrapper<Manager>().eq("id",id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增Manager数据
     * @param manager
     * @return
     */
    @PostMapping
    public Result add(@RequestBody Manager manager) {
        //调用managerMapper实现添加Manager
        if (managerMapper.insert(manager) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询Manager数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<Manager> findById(@PathVariable Integer id) {
        //调用managerMapper实现根据主键查询Manager
        Manager manager = managerMapper.selectById(id);
        return Result.success(manager);
    }

    /***
     * 查询Manager全部数据
     * @return
     */
    @GetMapping
    public Result<List<Manager>> findAll() {
        //调用managerMapper实现查询所有Manager
        List<Manager> list = managerMapper.selectList(null);
        return Result.success(list);
    }
}
