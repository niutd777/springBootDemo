package com.niutd.demo.service.impl;

import com.niutd.demo.dao.DemoDao;
import com.niutd.demo.entity.UserInfo;
import com.niutd.demo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: niutd
 * @date: 2019/3/14 10:49
 */
@Service
public class DemoServiceImpl implements DemoService {

    @Autowired
    private DemoDao demoDao;

    @Override
    public List<UserInfo> getUserList() {
        return demoDao.getUserList();
    }

    @Override
    public int addUser(UserInfo user) {
        return demoDao.addUser(user);
    }

    @Override
    public UserInfo getUser(int id) {
        return demoDao.getUser(id);
    }

    @Override
    public void deleteUser(int id) {
        demoDao.deleteUser(id);
    }

    @Override
    public int updateUser(UserInfo user) {
        return demoDao.updateUser(user);
    }
}
