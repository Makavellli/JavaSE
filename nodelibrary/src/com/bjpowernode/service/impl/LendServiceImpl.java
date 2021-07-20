package com.bjpowernode.service.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.dao.BookDao;
import com.bjpowernode.dao.LendDao;
import com.bjpowernode.dao.UserDao;
import com.bjpowernode.dao.impl.BookDaoImpl;
import com.bjpowernode.dao.impl.LendDaoImpl;
import com.bjpowernode.dao.impl.UserDaoImpl;
import com.bjpowernode.service.LendService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class LendServiceImpl implements LendService {

    private LendDao lendDaoImpl = new LendDaoImpl();
    private UserDao userDaoImpl = new UserDaoImpl();
    private BookDao bookDaoImpl = new BookDaoImpl();

    @Override
    public List<Lend> selectLends() {
        return lendDaoImpl.selectLends();
    }

    @Override
    public List<Lend> query(String lendName, String isbn) {
        return lendDaoImpl.query(lendName, isbn);
    }

    @Override
    public void add(int bookid, int userId) {
        Lend lend = new Lend();
        LocalDate now = LocalDate.now();
        lend.setId(UUID.randomUUID().toString());
        lend.setLendDate(now);
        lend.setReturnDate(now.plusDays(30));
        lend.setStatus(Constant.LEND_LEND);

        //更新图书借阅状态
        Book newBook = bookDaoImpl.updateBookStatus(bookid);
        //更新用户借阅状态
        User newUser = userDaoImpl.updateUserStatus(userId);
        //保存借阅记录
        lend.setBook(newBook);
        lend.setUser(newUser);
        lendDaoImpl.add(lend);
    }

    @Override
    public void returnBook(Lend lend) {
        //更新图书借阅状态
        bookDaoImpl.modify(lend.getBook());
        //更新用户借阅状态
        userDaoImpl.modify(lend.getUser());
        //新增一条借阅记录
        lend.setId(UUID.randomUUID().toString());
        lendDaoImpl.add(lend);
    }

    @Override
    public void modify(Lend lend) {
        
    }
}
