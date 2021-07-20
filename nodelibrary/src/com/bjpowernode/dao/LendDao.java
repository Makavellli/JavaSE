package com.bjpowernode.dao;

import com.bjpowernode.bean.Lend;

import java.util.List;

public interface LendDao {
    List<Lend> selectLends();

    List<Lend> query(String lendName, String isbn);

    void add(Lend lend);

    //�޸Ľ��ļ�¼ ����
    void modify(Lend lend);
}
