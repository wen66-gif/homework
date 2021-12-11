package com.fifth.controller;

import ch.qos.logback.core.pattern.color.ANSIConstants;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fifth.common.Result;
import com.fifth.domain.AnswerSummary;
import com.fifth.domain.Open;
import com.fifth.domain.Student;
import com.fifth.mapper.AnswerSummaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/answerSummary")
@CrossOrigin
public class AnswerSummaryController {

    @Autowired
    private AnswerSummaryMapper answerSummaryMapper;

    // 查看提交列表
    @GetMapping("/findList")
    public Result findSubmitList(
            @RequestParam int pageNum,
            @RequestParam int pageSize,
            @RequestParam int homeworkId,
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortOrder,
            @RequestParam(required = false,value = "className[]") String className[],
            @RequestParam int value){
        Page<AnswerSummary> page = new Page<>(pageNum, pageSize);
        OrderItem orderItem = new OrderItem();
        orderItem.setColumn(sortField);
        if (sortOrder!=null){
            if (sortOrder.equals("ascend")){
                orderItem.setAsc(true);
            }
            else {
                orderItem.setAsc(false);
            }
        }
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);
        page.setOrders(orderItems);
        List<String> classList = new ArrayList<>();
        if (className != null){
            for (String c : className) {
                classList.add(c);
            }
        }
        if (value==1){  // 查询已交列表
            return Result.success(answerSummaryMapper.findSubmitList(page, homeworkId, classList));
        }
        else {  // 查询未交列表
            return Result.success(answerSummaryMapper.findUnSubmitList(page, homeworkId, classList));
        }

    }

    /***
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用answerSummaryMapper实现根据主键删除
        if (answerSummaryMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "删除失败");
    }

    /***
     * 修改AnswerSummary数据
     * @param answerSummary
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody AnswerSummary answerSummary, @PathVariable Integer id) {
        //调用answerSummaryMapper实现修改AnswerSummary
        if (answerSummaryMapper.update(answerSummary, new UpdateWrapper<AnswerSummary>().eq("id", id)) > 0)
            return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增AnswerSummary数据
     * @param answerSummary
     * @return
     */
    @PostMapping
    public Result add(@RequestBody AnswerSummary answerSummary) {
        //调用answerSummaryMapper实现添加AnswerSummary
        if (answerSummaryMapper.insert(answerSummary) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询AnswerSummary数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<AnswerSummary> findById(@PathVariable Integer id) {
        //调用answerSummaryMapper实现根据主键查询AnswerSummary
        AnswerSummary answerSummary = answerSummaryMapper.selectById(id);
        return Result.success(answerSummary);
    }

    /***
     * 查询AnswerSummary全部数据
     * @return
     */
    @GetMapping
    public Result<List<AnswerSummary>> findAll() {
        //调用answerSummaryMapper实现查询所有AnswerSummary
        List<AnswerSummary> list = answerSummaryMapper.selectList(null);
        return Result.success(list);
    }
}
