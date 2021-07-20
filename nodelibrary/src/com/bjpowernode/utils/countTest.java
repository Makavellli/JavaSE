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
        //User 实体 测试用 String id,name;
        //当前测试以id来分组，具体请按开发场景修改
        list.add(new User("1", "1"));
        list.add(new User("1", "2"));
        list.add(new User("2", "2"));
        list.add(new User("2", "3"));
        list.add(new User("2", "4"));
        list.add(new User("3", "3"));

        //初始化一个map
        Map<String, List<User>> map = new HashMap<>();
        for (User user : list) {
            String key = user.getId();
            if (map.containsKey(key)) {
                //map中存在以此id作为的key，将数据存放当前key的map中
                map.get(key).add(user);
            } else {
                //map中不存在以此id作为的key，新建key用来存放数据
                List<User> userList = new ArrayList<>();
                userList.add(user);
                map.put(key, userList);
            }
        }
        //分组结束，map中的数据就是分组后的数据
        System.out.println(map);
    }
}
