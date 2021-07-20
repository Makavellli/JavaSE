package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.dao.BookDao;
import com.bjpowernode.dao.impl.BookDaoImpl;
import com.bjpowernode.service.BookService;
import javafx.scene.chart.PieChart;

import java.util.List;

public class BookServiceImpl implements BookService {
    private BookDao bookDaoImpl = new BookDaoImpl();

    @Override
    public List<Book> getAllBook() {
        return bookDaoImpl.getAllBook();
    }

    @Override
    public List<Book> queryBook(String bookName, String sbin) {
        return bookDaoImpl.queryBook(bookName, sbin);
    }

    @Override
    public void add(Book book) {
        bookDaoImpl.add(book);
    }

    @Override
    public void modify(Book book) {
        bookDaoImpl.modify(book);
    }

    @Override
    public void remove(Book book) {
        bookDaoImpl.remove(book);
    }

    @Override
    public List<PieChart.Data> countBooks() {
        return bookDaoImpl.countBooks();
    }

    @Override
    public Book getBookNameById(String bookId) {
        return bookDaoImpl.getBookNameById(bookId);
    }

    @Override
    public Book getIsbnById(String bookId) {
        return bookDaoImpl.getIsbnById(bookId);
    }
}
