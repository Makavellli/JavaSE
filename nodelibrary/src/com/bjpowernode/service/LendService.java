package com.bjpowernode.service;

import com.bjpowernode.bean.Lend;

import java.util.List;

public interface LendService {
    List<Lend> selectLends();

    List<Lend> query(String lendName, String isbn);

    void add(int bookId, int userId);

    void returnBook(Lend lend);

    void modify(Lend lend);
}
