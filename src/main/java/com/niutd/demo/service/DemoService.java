package com.niutd.demo.service;


import com.niutd.demo.entity.UserInfo;

import java.util.List;

/**
 * @author: niutd
 * @date: 2019/3/14 10:49
 */
public interface DemoService {

    List<UserInfo> getUserList();

    int addUser(UserInfo user);

    UserInfo getUser(int id);

    void deleteUser(int id);

    int updateUser(UserInfo user);
}
