package com.bjpowernode.utils;

import java.lang.reflect.Field;

public class ReflectTest {

    static class Person {
        private String name;
        private Integer age;
        private String sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Person(String name, Integer age, String sex) {
            this.name = name;
            this.age = age;
            this.sex = sex;
        }

        public Person() {
        }
    }

    public static void main(String[] args) throws NoSuchFieldException,
            SecurityException,
            IllegalArgumentException,
            IllegalAccessException {
        Person person = new Person();
        person.setName("VipMao");
        person.setAge(24);
        person.setSex("��");
        //ͨ��Class.getDeclaredField(String name)��ȡ���ӿڵ�ָ������ֵ��
        Field f1 = person.getClass().getDeclaredField("name");
        System.out.println("-----Class.getDeclaredField(String name)�÷�-------");
        f1.setAccessible(true);
        System.out.println(f1.get(person));
        System.out.println("-----Class.getDeclaredFields()�÷�-------");
        //ͨ��Class.getDeclaredFields()��ȡ���ӿڵ�ָ������ֵ��
        Field[] f2 = person.getClass().getDeclaredFields();
        for (Field field : f2) {
            field.setAccessible(true);
            System.out.println(field.get(person));
        }
        //�޸�����ֵ
        System.out.println("----�޸�name����------");
        f1.set(person, "Maoge");
        //�޸ĺ��ٱ��������Ե�ֵ
        Field[] f3 = person.getClass().getDeclaredFields();
        for (Field fields : f3) {
            fields.setAccessible(true);
            System.out.println(fields.get(person));
        }
    }
}
