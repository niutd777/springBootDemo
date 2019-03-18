package com.niutd.demo.dao;


import com.niutd.demo.entity.UserInfo;

import java.util.List;

/**
 * @author: niutd
 * @date: 2019/3/14 10:50
 */
public interface DemoDao {
    List<UserInfo> getUserList();

    int addUser(UserInfo user);

    UserInfo getUser(int id);

    void deleteUser(int id);

    int updateUser(UserInfo user);
}
