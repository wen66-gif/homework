package com.fifth.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/****
 * @Author:Anonym
 * @Description:Homework构建
 * @Date 2021/11/26 00:00
 *****/
@TableName(value = "homework")
public class Homework implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;//

    private String name;//作业名称

    private float score;//总分

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;//作业创建时间

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date updateTime;//作业更新时间

    private Integer courseId;//课程id

    @TableField(exist = false)
    private ClassHomework classHomework;//作业发布

    @TableField(exist = false)
    private List<Classes> classesList;

    @TableField(exist = false)
    private  AnswerSummary answerSummary;
    public Homework() {
    }

    public Homework(String name, float score, Date createTime, Integer courseId) {
        this.name = name;
        this.createTime = createTime;
        this.courseId = courseId;
        this.score = score;
    }

    public Homework(Integer id, String name, float score, Date updateTime) {
        this.id = id;
        this.name = name;
        this.score = score;
        this.updateTime = updateTime;
    }

    public AnswerSummary getAnswerSummary() {
        return answerSummary;
    }

    public void setAnswerSummary(AnswerSummary answerSummary) {
        this.answerSummary = answerSummary;
    }

    public ClassHomework getClassHomework() {
        return classHomework;
    }

    public void setClassHomework(ClassHomework classHomework) {
        this.classHomework = classHomework;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public List<Classes> getClassesList() {
        return classesList;
    }

    public void setClassesList(List<Classes> classesList) {
        this.classesList = classesList;
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
    public String getName() {
        return name;
    }

    //set方法
    public void setName(String name) {
        this.name = name;
    }

    //get方法
    public Date getCreateTime() {
        return createTime;
    }

    //set方法
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    //get方法
    public Integer getCourseId() {
        return courseId;
    }

    //set方法
    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    //get方法
    public float getScore() {
        return score;
    }

    //set方法
    public void setScore(float score) {
        this.score = score;
    }
}
