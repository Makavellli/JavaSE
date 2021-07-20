package com.bjpowernode.dao;

import com.bjpowernode.bean.User;

import java.util.List;

public interface UserDao {
    List<User> getUserList();

    void addUser(User user);

    void modify(User user);

    void remove(User user);

    void frozen(User user);

    User updateUserStatus(int userId);
}
