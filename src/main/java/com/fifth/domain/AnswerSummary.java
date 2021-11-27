package com.fifth.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author:Anonym
 * @Description:AnswerSummary构建
 * @Date 2021/11/26 00:00
 *****/
@TableName(value = "answer_summary")
public class AnswerSummary implements Serializable {

    @TableId(value = "id")
    private Integer id;//

    private String studentNo;//学生账号

    private Integer homeworkId;//作业id

    private Date finishTime;//完成时间

    private Integer score;//总分

    //get方法
    public Integer getId() {
        return id;
    }

    //set方法
    public void setId(Integer id) {
        this.id = id;
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
    public Date getFinishTime() {
        return finishTime;
    }

    //set方法
    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    //get方法
    public Integer getScore() {
        return score;
    }

    //set方法
    public void setScore(Integer score) {
        this.score = score;
    }
}
