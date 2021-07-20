package com.bjpowernode.dao;

import com.bjpowernode.bean.Lend;

import java.util.List;

public interface LendDao {
    List<Lend> selectLends();

    List<Lend> query(String lendName, String isbn);

    void add(Lend lend);

    //ÐÞ¸Ä½èÔÄ¼ÇÂ¼ Ðø½è
    void modify(Lend lend);
}
