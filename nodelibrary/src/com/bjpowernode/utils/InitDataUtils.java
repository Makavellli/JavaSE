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
        //��ʼ���û�����
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "����ң", "����", new BigDecimal(("101")), Constant.USER_LEND_NO));
        userList.add(new User(2, "�ƽ���", "����", new BigDecimal(("1")), Constant.USER_LEND_NO));
        userList.add(new User(3, "���½���", "����", new BigDecimal(("10000000")), Constant.USER_LEND_NO));
        userList.add(new User(4, "����", "����", new BigDecimal(("100000")), Constant.USER_LEND_NO));
        userList.add(new User(5, "����С��", "����", new BigDecimal(("500000")), Constant.USER_LEND_NO));
        userList.add(new User(6, "�����", "����", new BigDecimal(("500000")), Constant.USER_LEND_NO));
        userList.add(new User(7, "ʥ��", "����", new BigDecimal(("500000")), Constant.USER_LEND_NO));
        userList.add(new User(8, "��С��", "����", new BigDecimal(("500000")), Constant.USER_LEND_NO));
        initData(userList, Constant.USER_DATA_DIR, Constant.USER_DATA_FILE);

        //��ʼ��ͼ������
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1, "��Ա����������", "˹̹��˹����˹��", Constant.TYPE_COMPUTER, "24-7", "˹�ٷ��ܳ�����", Constant.STATUS_STORAGE));
        bookList.add(new Book(2, "�Ұ�����찲��", "�Ͷ�����", Constant.TYPE_LITERATURE, "24-13", "���й�������", Constant.STATUS_STORAGE));
        bookList.add(new Book(3, "���ﳤ��������", "�Ͷ�����", Constant.TYPE_MANAGEMENT, "24-30", "���й�������", Constant.STATUS_STORAGE));
        bookList.add(new Book(4, "���Ű��Ű�����", "Ү��", Constant.TYPE_ECONOMY, "110-1", "���ʳ�����", Constant.STATUS_STORAGE));
        bookList.add(new Book(5, "21�쾫ͨ��ˮ���� ", "���ү", Constant.TYPE_LITERATURE, "24-7", "��ƽ������", Constant.STATUS_STORAGE));
        bookList.add(new Book(6, "��Ч������׵����ͻ����21�����ƫ��", "����Գ", Constant.TYPE_COMPUTER, "0-0-7", "1024������", Constant.STATUS_STORAGE));
        bookList.add(new Book(7, "�����ŵ�������Java��", "����Գ", Constant.TYPE_ECONOMY, "110-1", "1024������", Constant.STATUS_STORAGE));
        bookList.add(new Book(8, "�����ŵ�������Python��", "����Գ", Constant.TYPE_ECONOMY, "110-2", "1024������", Constant.STATUS_STORAGE));
        initData(bookList, Constant.BOOK_DATA_DIR, Constant.BOOK_DATA_FILE);

        //��ʼ�����ļ�¼����
        List<Lend> lendList = new ArrayList<>();
        LocalDate now = LocalDate.parse("2021-06-01");
        lendList.add(new Lend(UUID.randomUUID().toString(), new Book(1, "��Ա����������", "˹̹��˹����˹��", Constant.TYPE_COMPUTER, "24-7", "˹�ٷ��ܳ�����", Constant.STATUS_LEND), new User(1, "����ң", "����", new BigDecimal(("1")), Constant.USER_LEND_YES), Constant.LEND_LEND, now, now.plusDays(30)));
        lendList.add(new Lend(UUID.randomUUID().toString(), new Book(6, "��Ч������׵����ͻ����21�����ƫ��", "����Գ", Constant.TYPE_COMPUTER, "0-0-7", "1024������", Constant.STATUS_LEND), new User(4, "����", "����", new BigDecimal(("100000")), Constant.USER_LEND_YES), Constant.LEND_LEND, now, now.plusDays(30)));
        initData(lendList, Constant.LEND_DATA_DIR, Constant.LEND_DATA_FILE);
    }

    public static void initData(List<?> dataList, String dirPath, String dataFilePath) {
        ObjectOutputStream objectOutputStream = null;
        ObjectInputStream objectInputStream = null;
        try {
            File dir = new File(dirPath);
            File userFile = new File(dataFilePath);

            //�ж�Ŀ¼�Ƿ����
            if (!dir.exists()) {
                System.out.println(dirPath + "Ŀ¼������,����֮!");
                dir.mkdir();
            }
            /**
             * �ж��ļ��Ƿ����
             * -----���������贴����ͬʱ���ӳ�ʼ���û����� ��ʼ����ɺ� ���û����ݱ�����user.txt
             * -----���������ȶ�ȡ�ļ����ݣ����ж��Ƿ��ȡ�����ݣ�
             * ----------�������������ӳ�ʼ���û����ݣ������û����ݱ�����user.txt��
             * ----------�������ݣ��򽫶�ȡ����ת����User���󷵻�ֵǰ̨ҳ�����չʾ
             */
            if (!userFile.exists()) {
                System.out.println(dataFilePath + "�ļ�������,����֮!");
                userFile.createNewFile();

                //������������л��������ã��Ѷ���ת���ֽ����ݵ�������ļ��б��棬�����������̳�Ϊ���л�����ʵ�ֶ���ĳ־ô洢��
                objectOutputStream = new ObjectOutputStream(new FileOutputStream(dataFilePath));
                objectOutputStream.writeObject(dataList);
            } else {
                System.out.println(dataFilePath + "�ļ�����,��ȡ�ļ�!");
                FileInputStream fileInputStream = new FileInputStream(dataFilePath);
                if (fileInputStream != null) {
                    objectInputStream = new ObjectInputStream(fileInputStream);
                    dataList = (List<?>) objectInputStream.readObject();
                    System.out.println("�ļ���������,�������!");
                    for (Object obj : dataList) {
                        System.out.println(obj);
                    }
                } else {
                    System.out.println("�ļ���������,��������!");

                    //������������л��������ã��Ѷ���ת���ֽ����ݵ�������ļ��б��棬�����������̳�Ϊ���л�����ʵ�ֶ���ĳ־ô洢��
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
}