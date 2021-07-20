package com.bjpowernode.utils;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;

import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InitDataUtils {

    public static void main(String[] args) {
        //初始化用户数据
        List<User> userList = new ArrayList<>();
        userList.add(new User(UUID.randomUUID().toString(), "李逍遥", "正常", new BigDecimal(("101")), Constant.USER_LEND_NO));
        userList.add(new User(UUID.randomUUID().toString(), "酒剑仙", "正常", new BigDecimal(("1")), Constant.USER_LEND_NO));
        userList.add(new User(UUID.randomUUID().toString(), "拜月教主", "正常", new BigDecimal(("10000000")), Constant.USER_LEND_NO));
        userList.add(new User(UUID.randomUUID().toString(), "阿七", "正常", new BigDecimal(("100000")), Constant.USER_LEND_NO));
        userList.add(new User(UUID.randomUUID().toString(), "唐钰小宝", "正常", new BigDecimal(("500000")), Constant.USER_LEND_NO));
        userList.add(new User(UUID.randomUUID().toString(), "李大婶", "正常", new BigDecimal(("500000")), Constant.USER_LEND_NO));
        userList.add(new User(UUID.randomUUID().toString(), "圣姑", "正常", new BigDecimal(("500000")), Constant.USER_LEND_NO));
        userList.add(new User(UUID.randomUUID().toString(), "王小虎", "正常", new BigDecimal(("500000")), Constant.USER_LEND_NO));
//        initData(userList, Constant.USER_DATA_DIR, Constant.USER_DATA_FILE);

        //初始化图书数据
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(UUID.randomUUID().toString(), "演员的自我修养", "斯坦尼斯拉夫斯基", Constant.TYPE_COMPUTER, "24-7", "斯蒂芬周出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(UUID.randomUUID().toString(), "我爱祖国天安门", "劳动人民", Constant.TYPE_LITERATURE, "24-13", "大中国出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(UUID.randomUUID().toString(), "万里长城永不倒", "劳动人民", Constant.TYPE_MANAGEMENT, "24-30", "大中国出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(UUID.randomUUID().toString(), "阿门阿门阿阿门", "耶稣", Constant.TYPE_ECONOMY, "110-1", "国际出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(UUID.randomUUID().toString(), "21天精通划水摸鱼 ", "葛大爷", Constant.TYPE_LITERATURE, "24-7", "躺平出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(UUID.randomUUID().toString(), "有效治疗腰椎间盘突出的21个民间偏方", "程序猿", Constant.TYPE_COMPUTER, "0-0-7", "1024出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(UUID.randomUUID().toString(), "从入门到入土（Java）", "程序猿", Constant.TYPE_ECONOMY, "110-1", "1024出版社", Constant.STATUS_STORAGE));
        bookList.add(new Book(UUID.randomUUID().toString(), "从入门到入土（Python）", "程序猿", Constant.TYPE_ECONOMY, "110-2", "1024出版社", Constant.STATUS_STORAGE));
//        initData(bookList, Constant.BOOK_DATA_DIR, Constant.BOOK_DATA_FILE);

        //初始化借阅记录数据
        List<Lend> lendList = new ArrayList<>();
//        LocalDate now = LocalDate.parse("2021-06-01");
//        lendList.add(new Lend(UUID.randomUUID().toString(), new Book(1, "演员的自我修养", "斯坦尼斯拉夫斯基", Constant.TYPE_COMPUTER, "24-7", "斯蒂芬周出版社", Constant.STATUS_LEND), new User(1, "李逍遥", "正常", new BigDecimal(("1")), Constant.USER_LEND_YES), Constant.LEND_LEND, now, now.plusDays(30)));
//        lendList.add(new Lend(UUID.randomUUID().toString(), new Book(6, "有效治疗腰椎间盘突出的21个民间偏方", "程序猿", Constant.TYPE_COMPUTER, "0-0-7", "1024出版社", Constant.STATUS_LEND), new User(4, "阿七", "正常", new BigDecimal(("100000")), Constant.USER_LEND_YES), Constant.LEND_LEND, now, now.plusDays(30)));
//        initData(lendList, Constant.LEND_DATA_DIR, Constant.LEND_DATA_FILE);
        lendDataModify();
    }

    public static void initData(List<?> dataList, String dirPath, String dataFilePath) {
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            File dir = new File(dirPath);
            File userFile = new File(dataFilePath);

            //判断目录是否存在
            if (!dir.exists()) {
                System.out.println(dirPath + "目录不存在,创建之!");
                dir.mkdir();
            }
            /**
             * 判断文件是否存在
             * -----若不存在需创建，同时增加初始化用户数据 初始化完成后 将用户数据保存至user.txt
             * -----若存在则先读取文件内容，并判断是否读取到数据：
             * ----------若无数据则增加初始化用户数据，并将用户数据保存至user.txt；
             * ----------若有数据，则将读取内容转换成User对象返回值前台页面进行展示
             */
            if (!userFile.exists()) {
                System.out.println(dataFilePath + "文件不存在,创建之!");
                userFile.createNewFile();

                //创建对象的序列化流，作用：把对象转成字节数据的输出到文件中保存，对象的输出过程称为序列化，可实现对象的持久存储。
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(dataFilePath));
                objectOutputStream.writeObject(dataList);
            } else {
                System.out.println(dataFilePath + "文件存在,读取文件!");
                FileInputStream fileInputStream = new FileInputStream(dataFilePath);
                if (fileInputStream != null) {
                    objectInputStream = new ObjectInputStream(fileInputStream);
                    dataList = (List<?>) objectInputStream.readObject();
                    System.out.println("文件内有数据,输出数据!");
                    for (Object obj : dataList) {
                        System.out.println(obj);
                    }
                } else {
                    System.out.println("文件内无数据,添加数据!");

                    //创建对象的序列化流，作用：把对象转成字节数据的输出到文件中保存，对象的输出过程称为序列化，可实现对象的持久存储。
                    objectOutputStream = new ObjectOutputStream(new FileOutputStream(dataFilePath));
                    objectOutputStream.writeObject(dataList);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void lendDataModify() {
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        LocalDate localDate = LocalDate.parse("2021-02-01");
        String dataFilePath = Constant.LEND_DATA_FILE;
        try {

            System.out.println(dataFilePath + "文件存在,读取文件!");
            FileInputStream fileInputStream = new FileInputStream(dataFilePath);

            objectInputStream = new ObjectInputStream(fileInputStream);
            List<Lend> dataList = (List<Lend>) objectInputStream.readObject();
            System.out.println("文件内有数据,输出数据!");
            dataList.forEach(dl -> {
                dl.setLendDate(localDate);
                dl.setReturnDate(localDate.plusDays(30));
                System.out.println(dl);
            });

            System.out.println(dataList);
            //创建对象的序列化流，作用：把对象转成字节数据的输出到文件中保存，对象的输出过程称为序列化，可实现对象的持久存储。
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(dataFilePath));
            objectOutputStream.writeObject(dataList);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
