package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.dao.LendDao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class LendDaoImpl implements LendDao {
    @Override
    public List<Lend> selectLends() {

        ObjectInputStream objectInputStream = null;
        List<Lend> lendList = new ArrayList<>();
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(Constant.LEND_DATA_FILE));
            if (objectInputStream != null) {
                lendList = (List<Lend>) objectInputStream.readObject();
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
        return lendList;
    }

    @Override
    public void add(Lend lend) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * 读取文件内用户数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.LEND_DATA_FILE));
            List<Lend> lendList_read = (List<Lend>) ois.readObject();
            lendList_read.add(lend);

            //将新的借阅列表保存至用户文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.LEND_DATA_FILE));
            oos.writeObject(lendList_read);
            System.out.println("将新增后的数据保存至用户文件!");
            System.out.println(lendList_read);

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

    //修改借阅记录 续借
    @Override
    public void modify(Lend lend) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        List<Lend> lendList_read;
        try {
            /**
             * 读取文件内借阅记录数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.LEND_DATA_FILE));
            lendList_read = (List<Lend>) ois.readObject();
            //获取id相同的借阅记录，并更新

            List<Lend> lendList_filter = lendList_read.stream().filter(le -> le.getId().equals(lend.getId())).collect(Collectors.toList());
            lendList_read.removeAll(lendList_filter);
            List<Lend> lendList_result = new ArrayList<>();
            lendList_filter.forEach(lf -> {
                lf.setReturnDate(lend.getReturnDate());
                lendList_result.add(lf);
            });
            lendList_read.addAll(lendList_result);

            //将新的借阅列表保存至图书文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.LEND_DATA_FILE));
            oos.writeObject(lendList_read);

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
}
