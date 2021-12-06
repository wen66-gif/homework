package com.fifth.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Serializable;

/****
 * @Author:Anonym
 * @Description:StudentAnswer构建
 * @Date 2021/11/26 00:00
 *****/
@TableName(value = "student_answer")
public class StudentAnswer implements Serializable {

    @TableId(value = "id")
    private Integer id;//

    private String answer;//学生答案

    private Integer result;//是否正确1-正确 2-错误

    private Float score;//题目得分

    private String studentNo;//学生id

    private Integer homeworkId;//作业id

    private Integer choiceId;//选择题id（二选一）

    private Integer unchoiceId;//非选择题id（二选一）

    //get方法
    public Integer getId() {
        return id;
    }

    //set方法
    public void setId(Integer id) {
        this.id = id;
    }

    //get方法
    public String getAnswer() {
        return answer;
    }

    //set方法
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    //get方法
    public Integer getResult() {
        return result;
    }

    //set方法
    public void setResult(Integer result) {
        this.result = result;
    }

    //get方法
    public Float getScore() {
        return score;
    }

    //set方法
    public void setScore(Float score) {
        this.score = score;
    }

    //get方法
    public String getStudentNo() {
        return studentNo;
    }

    //set方法
    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    //get方法
    public Integer getHomeworkId() {
        return homeworkId;
    }

    //set方法
    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }

    //get方法
    public Integer getChoiceId() {
        return choiceId;
    }

    //set方法
    public void setChoiceId(Integer choiceId) {
        this.choiceId = choiceId;
    }

    //get方法
    public Integer getUnchoiceId() {
        return unchoiceId;
    }

    //set方法
    public void setUnchoiceId(Integer unchoiceId) {
        this.unchoiceId = unchoiceId;
    }
}
