package com.bjpowernode.service.impl;

import com.bjpowernode.bean.User;
import com.bjpowernode.dao.UserDao;
import com.bjpowernode.dao.impl.UserDaoImpl;
import com.bjpowernode.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDao userDaoImpl = new UserDaoImpl();

    @Override
    public List<User> getUserlist() {
        List<User> userList = userDaoImpl.getUserList();
        return userList;
    }

    @Override
    public void addUser(User user) {
        userDaoImpl.addUser(user);
    }

    @Override
    public void modify(User user) {
        userDaoImpl.modify(user);
    }

    @Override
    public void remove(User user) {
        userDaoImpl.remove(user);
    }

    @Override
    public void frozen(User user) {
        userDaoImpl.frozen (user);
    }
}
