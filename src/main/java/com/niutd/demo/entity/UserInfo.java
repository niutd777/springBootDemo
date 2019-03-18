package com.niutd.demo.entity;

import javax.validation.constraints.NotBlank;

/**
 * @author: niutd
 * @date: 2019/3/14 11:01
 */
public class UserInfo {
    private int id;
    private String username;
    @NotBlank(message = "密码不能为空")
    private String password;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
