package com.fifth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "answer_summary")
public class AnswerSummary implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//

    @TableField(value = "student_no")
    private String no;//学生账号

    private Integer homeworkId;//作业id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date submitTime;//完成时间

    private Float score;//总分

    @TableField(exist = false)
    private Student student;//学生
    @TableField(exist = false)
    private Classes classes;//班级
    @TableField(exist = false)
    private String className;

    public AnswerSummary() {
    }

    public AnswerSummary(String no, Integer homeworkId, Date submitTime, Float score) {
        this.no = no;
        this.homeworkId = homeworkId;
        this.submitTime = submitTime;
        this.score = score;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }

    //get方法
    public Integer getId() {
        return id;
    }

    //set方法
    public void setId(Integer id) {
        this.id = id;
    }

    //get方法
    public String getNo() {
        return no;
    }

    //set方法
    public void setNo(String no) {
        this.no = no;
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
    public Date getSubmitTime() {
        return submitTime;
    }

    //set方法
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }

    //get方法
    public Float getScore() {
        return score;
    }

    //set方法
    public void setScore(Float score) {
        this.score = score;
    }
}
