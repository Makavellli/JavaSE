package com.bjpowernode.dao;

import com.bjpowernode.bean.Book;
import javafx.scene.chart.PieChart;

import java.util.List;

public interface BookDao {
    List<Book> getAllBook();

    List<Book> queryBook(String bookName, String sbin);

    void add(Book book);

    void modify(Book book);

    void remove(Book book);

    List<PieChart.Data> countBooks();

    Book updateBookStatus(String bookId);

    Book getBookNameById(String bookId);

    Book getIsbnById(String bookId);
}
