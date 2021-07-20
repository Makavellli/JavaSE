package com.bjpowernode.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    //ID
    private String id;

    //名称
    private String name;

    //状态
    private String status;

    //余额
    private BigDecimal money;

    //借阅状态
    private String isLend;

    public User() {
    }

    public User(String id, String name, String status, BigDecimal money, String isLend) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.money = money;
        this.isLend = isLend;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getLend() {
        return isLend;
    }

    public void setLend(String lend) {
        isLend = lend;
    }

    @Override
    public String toString() {

        String info = String.format("%s\t\t\t%s\t\t\t%s\t\t\t%s\t\t\t%s", this.id, this.name, this.status, this.money.toString(), this.isLend);
        return info;
    }

    public static void printUsers(List<User> userList) {
        System.out.println("[编号]\t\t\t[姓名]\t\t\t[状态]\t\t\t[余额]\t\t\t[借阅状态]");
        System.out.println("----------------------------------------------------------");
        userList.forEach(s -> System.out.println(s.toString()));
        System.out.println(" ");
    }

    public static void printUsers(User user) {
        System.out.println("[编号]\t\t\t[姓名]\t\t\t[状态]\t\t\t[余额]\t\t\t[借阅状态]");
        System.out.println("----------------------------------------------------------");
        System.out.println(user.toString());
        System.out.println(" ");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(name, user.name) && Objects.equals(status, user.status) && Objects.equals(money, user.money) && Objects.equals(isLend, user.isLend);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, status, money, isLend);
    }
}
