package com.bjpowernode.service;

import com.bjpowernode.bean.*;

import java.util.List;

public interface LendService {
    List<Lend> selectLends();

    List<Lend> query(String lendName, String isbn);

    void add(String bookId, String userId);

    void returnBook(Lend lend, User user, Book book);

    void modify(Lend lend);
}
