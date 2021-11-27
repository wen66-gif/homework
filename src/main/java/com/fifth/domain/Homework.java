package com.fifth.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author:Anonym
 * @Description:Homework构建
 * @Date 2021/11/26 00:00
 *****/
@TableName(value = "homework")
public class Homework implements Serializable {

    @TableId(value = "id")
    private Integer id;//

    private String name;//作业名称

    private Date createTime;//作业创建时间

    private Integer courseId;//课程id

    private String score;//总分

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
    public String getScore() {
        return score;
    }

    //set方法
    public void setScore(String score) {
        this.score = score;
    }
}
