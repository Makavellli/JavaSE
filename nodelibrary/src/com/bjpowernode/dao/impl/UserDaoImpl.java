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

    //����û�
    @Override
    public void addUser(User user) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * ��ȡ�ļ����û�����
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            List<User> userList_read = (List<User>) ois.readObject();

            user.setId(UUID.randomUUID().toString());
            userList_read.add(user);

            //���µ�User�б������û��ļ���
            oos = new ObjectOutputStream(new FileOutputStream(Constant.USER_DATA_FILE));
            oos.writeObject(userList_read);
            System.out.println("������������ݱ������û��ļ�!");
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

    //�޸��û�
    @Override
    public void modify(User user) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        List<User> userList_read;
        try {
            /**
             * ��ȡ�ļ����û�����
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            userList_read = (List<User>) ois.readObject();
            //��ȡid��ͬ���û���������
            for (int i = 0; i < userList_read.size(); i++) {
                if (user.getId().equals(userList_read.get(i).getId())) {
                    userList_read.set(i, user);
                    break;
                }
            }

            //���µ�User�б������û��ļ���
            oos = new ObjectOutputStream(new FileOutputStream(Constant.USER_DATA_FILE));
            oos.writeObject(userList_read);
            System.out.println("���޸ĺ�����ݱ������û��ļ�!");
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

    //ɾ���û�
    @Override
    public void remove(User user) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * ��ȡ�ļ����û�����
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            List<User> userList_read = (List<User>) ois.readObject();
            //��ȡid��ͬ���û�����ɾ��
            for (int i = 0; i < userList_read.size(); i++) {
                if (user.getId() == userList_read.get(i).getId()) {
                    userList_read.remove(i);
                    break;
                }
            }

            //���µ�User�б������û��ļ���
            oos = new ObjectOutputStream(new FileOutputStream(Constant.USER_DATA_FILE));
            oos.writeObject(userList_read);
            System.out.println("��ɾ��������ݱ������û��ļ�!");
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

    //�����û�
    @Override
    public void frozen(User user) {

        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * ��ȡ�ļ����û�����
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            List<User> userList_read = (List<User>) ois.readObject();
            //��ȡid��ͬ���û�����ɾ��
            for (int i = 0; i < userList_read.size(); i++) {
                if (user.getId() == userList_read.get(i).getId()) {
                    userList_read.get(i).setStatus(Constant.USER_FROZEN);
                    break;
                }
            }

            //���µ�User�б������û��ļ���
            oos = new ObjectOutputStream(new FileOutputStream(Constant.USER_DATA_FILE));
            oos.writeObject(userList_read);
            System.out.println("���������û���Ϣ�������û��ļ�!");
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
             * ��ȡ�ļ����û�����
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.USER_DATA_FILE));
            List<User> userList_read = (List<User>) ois.readObject();
            List<User> queryList;
            //��ȡid��ͬ���û��������½���״̬
            queryList = userList_read.stream().filter(u -> u.getId().equals(userId)).collect(Collectors.toList());
            //System.out.println("�����������û��У�" + queryList);
            newUser = queryList.get(0);
            //�Ӷ�ȡ���û��н������������û�ɾ��
            userList_read.removeAll(queryList);
            //System.out.println("ɾ������û��б�" + userList_read);
            //���º��ٽ��û���ӵ��û��б�
            queryList.forEach(queryUser -> {
                queryUser.setLend(Constant.USER_LEND_YES);
                userList_read.add(queryUser);
            });

            //System.out.println("���º�Ĵ������û��б�:" + userList_read);

            //���µ�User�б������û��ļ���
            oos = new ObjectOutputStream(new FileOutputStream(Constant.USER_DATA_FILE));
            oos.writeObject(userList_read);
            System.out.println("�����Ľ���״̬����û���Ϣ�������û��ļ�!");
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
