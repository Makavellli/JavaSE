package com.bjpowernode.bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/*
    借书
 */
public class Lend implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    public Lend() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //借出的书籍
    private String bookId;

    //借阅者
    private String userID;

    //状态
    private String status;

    //出借日期
    private LocalDate lendDate;

    //归还日期
    private LocalDate returnDate;

    //记录是否归档
    private Boolean isArchive;

    public Boolean getArchive() {
        return isArchive;
    }

    public void setArchive(Boolean archive) {
        isArchive = archive;
    }

    public Lend(String bookId, String userID, String status, LocalDate lendDate, LocalDate returnDate, Boolean isArchive) {
        this.bookId = bookId;
        this.userID = userID;
        this.status = status;
        this.lendDate = lendDate;
        this.returnDate = returnDate;
        this.isArchive = isArchive;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
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

    @Override
    public String toString() {
        return "Lend{" +
                "bookId='" + bookId + '\'' +
                ", userID='" + userID + '\'' +
                ", status='" + status + '\'' +
                ", lendDate=" + lendDate +
                ", returnDate=" + returnDate +
                ", isArchive=" + isArchive +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lend lend = (Lend) o;
        return Objects.equals(bookId, lend.bookId) && Objects.equals(userID, lend.userID) && Objects.equals(status, lend.status) && Objects.equals(lendDate, lend.lendDate) && Objects.equals(returnDate, lend.returnDate) && Objects.equals(isArchive, lend.isArchive);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, userID, status, lendDate, returnDate, isArchive);
    }
}
