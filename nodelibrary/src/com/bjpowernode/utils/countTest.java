package com.bjpowernode.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class countTest {

    public static void main(String[] args) {
        countTest.test();
    }

    static class User {
        private String id;
        private String name;

        public User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static void test() {

        List<User> list = new ArrayList<>();
        //User ʵ�� ������ String id,name;
        //��ǰ������id�����飬�����밴���������޸�
        list.add(new User("1", "1"));
        list.add(new User("1", "2"));
        list.add(new User("2", "2"));
        list.add(new User("2", "3"));
        list.add(new User("2", "4"));
        list.add(new User("3", "3"));

        //��ʼ��һ��map
        Map<String, List<User>> map = new HashMap<>();
        for (User user : list) {
            String key = user.getId();
            if (map.containsKey(key)) {
                //map�д����Դ�id��Ϊ��key�������ݴ�ŵ�ǰkey��map��
                map.get(key).add(user);
            } else {
                //map�в������Դ�id��Ϊ��key���½�key�����������
                List<User> userList = new ArrayList<>();
                userList.add(user);
                map.put(key, userList);
            }
        }
        //���������map�е����ݾ��Ƿ���������
        System.out.println(map);
    }
}
