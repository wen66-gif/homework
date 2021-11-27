package com.fifth.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/****
 * @Author:Anonym
 * @Description:Choice构建
 * @Date 2021/11/26 00:00
 *****/
@TableName(value = "choice")
public class Choice implements Serializable {

    @TableId(value = "id")
    private Integer id;//

    private Integer no;//题号

    private String title;//题干

    private String A;//A选项

    private String B;//B选项

    private String C;//C选项

    private String D;//D选项

    private Integer type;//题目类型

    private String trueAnswer;//正确答案

    private Integer score;//分数

    private Integer homeworkId;//作业id

    //get方法
    public Integer getId() {
        return id;
    }

    //set方法
    public void setId(Integer id) {
        this.id = id;
    }

    //get方法
    public Integer getNo() {
        return no;
    }

    //set方法
    public void setNo(Integer no) {
        this.no = no;
    }

    //get方法
    public String getTitle() {
        return title;
    }

    //set方法
    public void setTitle(String title) {
        this.title = title;
    }

    //get方法
    public String getA() {
        return A;
    }

    //set方法
    public void setA(String A) {
        this.A = A;
    }

    //get方法
    public String getB() {
        return B;
    }

    //set方法
    public void setB(String B) {
        this.B = B;
    }

    //get方法
    public String getC() {
        return C;
    }

    //set方法
    public void setC(String C) {
        this.C = C;
    }

    //get方法
    public String getD() {
        return D;
    }

    //set方法
    public void setD(String D) {
        this.D = D;
    }

    //get方法
    public Integer getType() {
        return type;
    }

    //set方法
    public void setType(Integer type) {
        this.type = type;
    }

    //get方法
    public String getTrueAnswer() {
        return trueAnswer;
    }

    //set方法
    public void setTrueAnswer(String trueAnswer) {
        this.trueAnswer = trueAnswer;
    }

    //get方法
    public Integer getScore() {
        return score;
    }

    //set方法
    public void setScore(Integer score) {
        this.score = score;
    }

    //get方法
    public Integer getHomeworkId() {
        return homeworkId;
    }

    //set方法
    public void setHomeworkId(Integer homeworkId) {
        this.homeworkId = homeworkId;
    }
}
