package com.fifth.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/****
 * @Author:Anonym
 * @Description:Student构建
 * @Date 2021/11/26 00:00
 *****/
@TableName(value = "student")
public class Student implements Serializable {

    @TableId(value = "no")
    private String no;//

    private String name;//

    private Integer sex;//

    private String password;//

    private Integer classId;//

    public Student() {
    }

    public Student(String no, String name, String password) {
        this.no = no;
        this.name = name;
        this.password = password;
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
    public String getName() {
        return name;
    }

    //set方法
    public void setName(String name) {
        this.name = name;
    }

    //get方法
    public Integer getSex() {
        return sex;
    }

    //set方法
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    //get方法
    public String getPassword() {
        return password;
    }

    //set方法
    public void setPassword(String password) {
        this.password = password;
    }

    //get方法
    public Integer getClassId() {
        return classId;
    }

    //set方法
    public void setClassId(Integer classId) {
        this.classId = classId;
    }
}
