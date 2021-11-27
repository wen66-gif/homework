package com.fifth.common;

import java.io.Serializable;

/**
 * @author: Anonym
 * @data: 2021/11/25 12:13
 */
public class UserInfo implements Serializable {
    private Integer role;
    private String userName;
    private String password;
    private String code;

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
