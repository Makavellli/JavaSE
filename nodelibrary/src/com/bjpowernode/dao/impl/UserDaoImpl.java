package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.UserDao;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserDaoImpl implements UserDao {
    @Override
    public List<User> getUserList() {
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream oos = null;

        List<User> userList = new ArrayList<User>();
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            if (objectInputStream != null) {
                userList = (List<User>) objectInputStream.readObject();
                User.printUsers(userList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return userList;
    }

    //添加用户
    @Override
    public void addUser(User user) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * 读取文件内用户数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            List<User> userList_read = (List<User>) ois.readObject();

            user.setId(UUID.randomUUID().toString());
            userList_read.add(user);

            //将新的User列表保存至用户文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.USER_DATA_FILE));
            oos.writeObject(userList_read);
            System.out.println("将新增后的数据保存至用户文件!");
            User.printUsers(userList_read);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //修改用户
    @Override
    public void modify(User user) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        List<User> userList_read;
        try {
            /**
             * 读取文件内用户数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            userList_read = (List<User>) ois.readObject();
            //获取id相同的用户，并更新
            for (int i = 0; i < userList_read.size(); i++) {
                if (user.getId().equals(userList_read.get(i).getId())) {
                    userList_read.set(i, user);
                    break;
                }
            }

            //将新的User列表保存至用户文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.USER_DATA_FILE));
            oos.writeObject(userList_read);
            System.out.println("将修改后的数据保存至用户文件!");
            User.printUsers(userList_read);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //删除用户
    @Override
    public void remove(User user) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * 读取文件内用户数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            List<User> userList_read = (List<User>) ois.readObject();
            //获取id相同的用户，并删除
            for (int i = 0; i < userList_read.size(); i++) {
                if (user.getId() == userList_read.get(i).getId()) {
                    userList_read.remove(i);
                    break;
                }
            }

            //将新的User列表保存至用户文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.USER_DATA_FILE));
            oos.writeObject(userList_read);
            System.out.println("将删除后的数据保存至用户文件!");
            User.printUsers(userList_read);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //冻结用户
    @Override
    public void frozen(User user) {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * 读取文件内用户数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            List<User> userList_read = (List<User>) ois.readObject();
            //获取id相同的用户，并删除
            for (int i = 0; i < userList_read.size(); i++) {
                if (user.getId() == userList_read.get(i).getId()) {
                    userList_read.get(i).setStatus(Constant.USER_FROZEN);
                    break;
                }
            }

            //将新的User列表保存至用户文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.USER_DATA_FILE));
            oos.writeObject(userList_read);
            System.out.println("将冻结后的用户信息保存至用户文件!");
            User.printUsers(userList_read);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public User updateUserStatus(String userId) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        User newUser;
        try {
            /**
             * 读取文件内用户数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            List<User> userList_read = (List<User>) ois.readObject();
            List<User> queryList;
            //获取id相同的用户，并更新借阅状态
            queryList = userList_read.stream().filter(u -> u.getId().equals(userId)).collect(Collectors.toList());
            //System.out.println("符合条件的用户有：" + queryList);
            newUser = queryList.get(0);
            //从读取的用户中将符合条件的用户删除
            userList_read.removeAll(queryList);
            //System.out.println("删除后的用户列表：" + userList_read);
            //更新后再讲用户添加到用户列表
            queryList.forEach(queryUser -> {
                queryUser.setLend(Constant.USER_LEND_YES);
                userList_read.add(queryUser);
            });

            //System.out.println("更新后的待保存用户列表:" + userList_read);

            //将新的User列表保存至用户文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.USER_DATA_FILE));
            oos.writeObject(userList_read);
            System.out.println("将更改借阅状态后的用户信息保存至用户文件!");
            User.printUsers(userList_read);
            return newUser;
        } catch (
                Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new User();
    }

    @Override
    public User getUserById(String userID) {

        ObjectInputStream objectInputStream = null;
        List<User> userList;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            userList = (List<User>) objectInputStream.readObject();
            if (objectInputStream != null) {

                List<User> userListCheck = userList.stream().filter(s -> s.getId().equals(userID)).collect(Collectors.toList());
                if (userListCheck != null) {
                    return userListCheck.get(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
