package com.fifth.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/****
 * @Author:Anonym
 * @Description:ClassHomeork构建
 * @Date 2021/11/26 00:00
 *****/
@TableName(value = "class_homework")
public class ClassHomework implements Serializable {

    @TableId(value = "id")
    private Integer id;//

    private Integer homeworkId;//作业id

    private Integer classId;//班级id

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date publishTime;//发布时间

    //get方法
    public Integer getId() {
        return id;
    }

    //set方法
    public void setId(Integer id) {
        this.id = id;
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
    public Integer getClassId() {
        return classId;
    }

    //set方法
    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    //get方法
    public Date getPublishTime() {
        return publishTime;
    }

    //set方法
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
