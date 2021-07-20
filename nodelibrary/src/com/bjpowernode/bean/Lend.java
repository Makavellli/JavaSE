package com.bjpowernode.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/*
    借书
 */
public class Lend implements Serializable {

    private static final long serialVersionUID = 1L;

    private String uuid;

    //借出的书籍
    private Book book;

    //借阅者
    private User user;

    //状态
    private String status;

    //出借日期
    private LocalDate lendDate;

    //归还日期
    private LocalDate returnDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lend lend = (Lend) o;
        return uuid == lend.uuid &&
                Objects.equals(book, lend.book) &&
                Objects.equals(user, lend.user) &&
                Objects.equals(status, lend.status) &&
                Objects.equals(lendDate, lend.lendDate) &&
                Objects.equals(returnDate, lend.returnDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, book, user, status, lendDate, returnDate);
    }

    public String getId() {
        return uuid;
    }

    public void setId(String uuid) {
        this.uuid = uuid;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getLendDate() {
        return lendDate;
    }

    public void setLendDate(LocalDate lendDate) {
        this.lendDate = lendDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Lend() {
    }

    public Lend(String uuid, Book book, User user, String status, LocalDate lendDate, LocalDate returnDate) {
        this.uuid = uuid;
        this.book = book;
        this.user = user;
        this.status = status;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Lend{" +
                "uuid='" + uuid + '\'' +
                ", book=" + book +
                ", user=" + user +
                ", status='" + status + '\'' +
                ", lendDate=" + lendDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
