package com.bjpowernode.service;

import com.bjpowernode.bean.User;

import java.util.List;

public interface UserService {
    List<User> getUserlist();

    void addUser(User user);

    void modify(User user);

    void remove(User user);

    void frozen(User user);

    User getUserNameById(String userID);
}
