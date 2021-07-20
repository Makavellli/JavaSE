package com.bjpowernode.dao.impl;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.dao.BookDao;
import javafx.scene.chart.PieChart;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class BookDaoImpl implements BookDao {

    @Override
    public List<Book> getAllBook() {
        ObjectInputStream objectInputStream = null;
        List<Book> bookList = new ArrayList<>();
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            if (objectInputStream != null) {
                bookList = (List<Book>) objectInputStream.readObject();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bookList;
    }

    //查询图书
    @Override
    public List<Book> queryBook(String bookName, String sbin) {
        ObjectInputStream objectInputStream = null;
        List<Book> bookList;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            bookList = (List<Book>) objectInputStream.readObject();
            if (objectInputStream != null) {

                List<Book> bookListCheck;

                /**
                 * 若bookname和isbn均为空,则直接返回查询结果
                 */
                if ("".equals(bookName) && "".equals(sbin)) {
                    return bookList;
                } else if (!"".equals(bookName) && !"".equals(sbin)) {
                    /**
                     * 若bookname和isbn均不为空 分别对bookname和isbin进行过滤
                     */
                    bookListCheck = bookList.stream().filter(s -> s.getBookName().contains(bookName)).collect(Collectors.toList());
                    bookListCheck = bookListCheck.stream().filter(s -> s.getIsbn().contains(sbin)).collect(Collectors.toList());
                    return bookListCheck;
                } else {
                    if (!"".equals(bookName)) {
                        bookListCheck = bookList.stream().filter(s -> s.getBookName().contains(bookName)).collect(Collectors.toList());
                        return bookListCheck;
                    }
                    if (!"".equals(sbin)) {
                        bookListCheck = bookList.stream().filter(s -> s.getIsbn().contains(sbin)).collect(Collectors.toList());
                        return bookListCheck;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (objectInputStream != null) {
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>();
    }

    //添加图书
    @Override
    public void add(Book book) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * 读取文件内用户数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            List<Book> bookList_read = (List<Book>) ois.readObject();
            if (bookList_read != null) {
                //获取最后一本图书的id，加1 赋值为新增图书的id
                book.setId(bookList_read.get(bookList_read.size() - 1).getId() + new Random().nextInt(1000));
                bookList_read.add(book);
            } else {
                book.setId(1);
                bookList_read.add(book);
            }

            //将新的Book列表保存至图书文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_DATA_FILE));
            oos.writeObject(bookList_read);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //修改图书
    @Override
    public void modify(Book book) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        List<Book> bookList_read;
        try {
            /**
             * 读取文件内用户数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            bookList_read = (List<Book>) ois.readObject();
            //获取id相同的用户，并更新
            for (int i = 0; i < bookList_read.size(); i++) {
                if (book.getId() == bookList_read.get(i).getId()) {
                    bookList_read.set(i, book);
                    break;
                }
            }

            //将新的Book列表保存至图书文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_DATA_FILE));
            oos.writeObject(bookList_read);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //删除图书
    @Override
    public void remove(Book book) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * 读取文件内图书数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            List<Book> bookList_read = (List<Book>) ois.readObject();
            //获取id相同的用户，并删除
            for (int i = 0; i < bookList_read.size(); i++) {
                if (book.getId() == bookList_read.get(i).getId()) {
                    bookList_read.remove(i);
                    break;
                }
            }

            //将新的User列表保存至用户文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_DATA_FILE));
            oos.writeObject(bookList_read);
//            System.out.println("保存图书!");
//            System.out.println(bookList_read);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 图书统计
     *
     * @return
     */
    @Override
    public List<PieChart.Data> countBooks() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * 读取文件内图书数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            List<Book> bookList_read = (List<Book>) ois.readObject();

            //按图书类型字段分组统计读取到的图书
//            System.out.println("读取到的图书:\n" + bookList_read);

            Map<String, List<Book>> collect = bookList_read.stream().collect(Collectors.groupingBy(Book::getType));
//            System.out.println(collect);

            //遍历collect,将统计数据封装为List<PieChart.Data>,并返回
            List<PieChart.Data> dataList = new ArrayList<>();
            collect.forEach((key, value) -> {
                PieChart.Data data = new PieChart.Data(key, value.size());
                dataList.add(data);
            });

            return dataList;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Book updateBookStatus(int bookId) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        List<Book> bookList_read;
        Book newBook = new Book();
        try {
            /**
             * 读取文件内用户数据
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            bookList_read = (List<Book>) ois.readObject();
            List<Book> bookList_qurey;

            //获取id相同的用户，并更新
            bookList_qurey = bookList_read.stream().filter(b -> b.getId() == bookId).collect(Collectors.toList());
            newBook = bookList_qurey.get(0);

            //将符合条件的图书删除
            bookList_read.removeAll(bookList_qurey);
            //更新后再将图书添加到图书列表
            bookList_qurey.forEach(bq -> {
                bq.setStatus(Constant.STATUS_LEND);
                bookList_read.add(bq);
            });
            //将新的图书列表保存至图书文件内
            oos = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_DATA_FILE));
            oos.writeObject(bookList_read);
            System.out.println("保存图书!");
//            System.out.println(bookList_read);
            return newBook;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new Book();
    }
}
