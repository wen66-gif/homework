package com.fifth.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/****
 * @Author:Anonym
 * @Description:Manager构建
 * @Date 2021/11/26 00:00
 *****/
@TableName(value = "manager")
public class Manager implements Serializable {

    @TableId(value = "id")
    private Integer id;//

    private String account;//

    private String password;//

    public Manager() {
    }

    public Manager(String account, String password) {
        this.account = account;
        this.password = password;
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
    public String getAccount() {
        return account;
    }

    //set方法
    public void setAccount(String account) {
        this.account = account;
    }

    //get方法
    public String getPassword() {
        return password;
    }

    //set方法
    public void setPassword(String password) {
        this.password = password;
    }
}
