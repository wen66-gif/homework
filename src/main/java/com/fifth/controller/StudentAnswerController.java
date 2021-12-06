package com.fifth.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fifth.common.Result;
import com.fifth.domain.AnswerSummary;
import com.fifth.domain.StudentAnswer;
import com.fifth.mapper.HomeworkMapper;
import com.fifth.mapper.StudentAnswerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/****
 * @Author:Anonym
 * @Description:
 * @Date 2021/11/26 00:00
 *****/

@RestController
@RequestMapping("/studentAnswer")
@CrossOrigin
public class StudentAnswerController {

    @Autowired
    private StudentAnswerMapper studentAnswerMapper;

    // 获取学生答案
    @GetMapping("/getStudentAnswer")
    public Result getStudentAnswer(@RequestParam int studentNo, @RequestParam int homeworkId) {
        try{
            List<StudentAnswer> studentAnswers = studentAnswerMapper.selectList(new QueryWrapper<StudentAnswer>()
                    .eq("student_no", studentNo).eq("homework_id", homeworkId));
            return Result.success(studentAnswers);
        }catch (Exception e){
            return Result.error("-1","获取学生答案失败");
        }
    }


    /***
     * 根据ID删除数据
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Result delete(@PathVariable Integer id) {
        //调用studentAnswerMapper实现根据主键删除
        if (studentAnswerMapper.deleteById(id) > 0) return Result.success();
        return Result.error("-1", "删除失败");
    }

    /***
     * 修改StudentAnswer数据
     * @param studentAnswer
     * @param id
     * @return
     */
    @PutMapping(value = "/{id}")
    public Result update(@RequestBody StudentAnswer studentAnswer, @PathVariable Integer id) {
        //设置主键值
        studentAnswer.setId(id);
        //调用studentAnswerMapper实现修改StudentAnswer
        if (studentAnswerMapper.update(studentAnswer, new UpdateWrapper<StudentAnswer>().eq("id",id)) > 0) return Result.success();
        return Result.error("-1", "修改失败");
    }

    /***
     * 新增StudentAnswer数据
     * @param studentAnswer
     * @return
     */
    @PostMapping
    public Result add(@RequestBody StudentAnswer studentAnswer) {
        //调用studentAnswerMapper实现添加StudentAnswer
        if (studentAnswerMapper.insert(studentAnswer) > 0) return Result.success();
        return Result.error("-1", "新增失败");
    }

    /***
     * 根据ID查询StudentAnswer数据
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Result<StudentAnswer> findById(@PathVariable Integer id) {
        //调用studentAnswerMapper实现根据主键查询StudentAnswer
        StudentAnswer studentAnswer = studentAnswerMapper.selectById(id);
        return Result.success(studentAnswer);
    }

    /***
     * 查询StudentAnswer全部数据
     * @return
     */
    @GetMapping
    public Result<List<StudentAnswer>> findAll() {
        //调用studentAnswerMapper实现查询所有StudentAnswer
        List<StudentAnswer> list = studentAnswerMapper.selectList(null);
        return Result.success(list);
    }
}
