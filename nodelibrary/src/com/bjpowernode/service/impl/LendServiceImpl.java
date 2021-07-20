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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LendServiceImpl implements LendService {

    private LendDao lendDaoImpl = new LendDaoImpl();
    private UserDao userDaoImpl = new UserDaoImpl();
    private BookDao bookDaoImpl = new BookDaoImpl();

    @Override
    public List<Lend> selectLends() {
        return lendDaoImpl.selectLends();
    }

    @Override
    public List<Lend> query(String bookName, String isbn) {
        List<Book> bookList = bookDaoImpl.queryBook(bookName, isbn);
        List<Lend> lendList = lendDaoImpl.selectLends();
        List<Lend> resultList = new ArrayList<>();
        bookList.forEach(b -> {
            resultList.addAll(lendList.stream().filter(l -> l.getBookId() == b.getId()).collect(Collectors.toList()));
        });
        return resultList;
    }

    @Override
    public void add(String bookid, String userId) {
        Lend lend = new Lend();
        LocalDate now = LocalDate.now();
        lend.setId(UUID.randomUUID().toString());
        lend.setLendDate(now);
        lend.setReturnDate(now.plusDays(30));
        lend.setStatus(Constant.LEND_LEND);

        //����ͼ�����״̬
        Book newBook = bookDaoImpl.updateBookStatus(bookid);
        //�����û�����״̬
        User newUser = userDaoImpl.updateUserStatus(userId);
        //������ļ�¼
        lend.setBookId(bookid);
        lend.setUserID(userId);
        lendDaoImpl.add(lend);
    }

    @Override
    public void returnBook(Lend lend, User user, Book book) {

        //�����û�����״̬
        userDaoImpl.modify(user);

        //����ͼ�����״̬
        bookDaoImpl.modify(book);

        //����һ�����ļ�¼
        lend.setId(UUID.randomUUID().toString());
        lendDaoImpl.add(lend);
    }

    @Override
    public void modify(Lend lend) {

    }
}
