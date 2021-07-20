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

    //��ѯͼ��
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
                 * ��bookname��isbn��Ϊ��,��ֱ�ӷ��ز�ѯ���
                 */
                if ("".equals(bookName) && "".equals(sbin)) {
                    return bookList;
                } else if (!"".equals(bookName) && !"".equals(sbin)) {
                    /**
                     * ��bookname��isbn����Ϊ�� �ֱ��bookname��isbin���й���
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

    //���ͼ��
    @Override
    public void add(Book book) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * ��ȡ�ļ����û�����
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            List<Book> bookList_read = (List<Book>) ois.readObject();
            if (bookList_read != null) {
                //��ȡ���һ��ͼ���id����1 ��ֵΪ����ͼ���id
                book.setId(bookList_read.get(bookList_read.size() - 1).getId() + new Random().nextInt(1000));
                bookList_read.add(book);
            } else {
                book.setId(1);
                bookList_read.add(book);
            }

            //���µ�Book�б�����ͼ���ļ���
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

    //�޸�ͼ��
    @Override
    public void modify(Book book) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        List<Book> bookList_read;
        try {
            /**
             * ��ȡ�ļ����û�����
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            bookList_read = (List<Book>) ois.readObject();
            //��ȡid��ͬ���û���������
            for (int i = 0; i < bookList_read.size(); i++) {
                if (book.getId() == bookList_read.get(i).getId()) {
                    bookList_read.set(i, book);
                    break;
                }
            }

            //���µ�Book�б�����ͼ���ļ���
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

    //ɾ��ͼ��
    @Override
    public void remove(Book book) {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * ��ȡ�ļ���ͼ������
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            List<Book> bookList_read = (List<Book>) ois.readObject();
            //��ȡid��ͬ���û�����ɾ��
            for (int i = 0; i < bookList_read.size(); i++) {
                if (book.getId() == bookList_read.get(i).getId()) {
                    bookList_read.remove(i);
                    break;
                }
            }

            //���µ�User�б������û��ļ���
            oos = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_DATA_FILE));
            oos.writeObject(bookList_read);
//            System.out.println("����ͼ��!");
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
     * ͼ��ͳ��
     *
     * @return
     */
    @Override
    public List<PieChart.Data> countBooks() {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            /**
             * ��ȡ�ļ���ͼ������
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            List<Book> bookList_read = (List<Book>) ois.readObject();

            //��ͼ�������ֶη���ͳ�ƶ�ȡ����ͼ��
//            System.out.println("��ȡ����ͼ��:\n" + bookList_read);

            Map<String, List<Book>> collect = bookList_read.stream().collect(Collectors.groupingBy(Book::getType));
//            System.out.println(collect);

            //����collect,��ͳ�����ݷ�װΪList<PieChart.Data>,������
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
             * ��ȡ�ļ����û�����
             */
            ois = new ObjectInputStream(new FileInputStream(Constant.BOOK_DATA_FILE));
            bookList_read = (List<Book>) ois.readObject();
            List<Book> bookList_qurey;

            //��ȡid��ͬ���û���������
            bookList_qurey = bookList_read.stream().filter(b -> b.getId() == bookId).collect(Collectors.toList());
            newBook = bookList_qurey.get(0);

            //������������ͼ��ɾ��
            bookList_read.removeAll(bookList_qurey);
            //���º��ٽ�ͼ����ӵ�ͼ���б�
            bookList_qurey.forEach(bq -> {
                bq.setStatus(Constant.STATUS_LEND);
                bookList_read.add(bq);
            });
            //���µ�ͼ���б�����ͼ���ļ���
            oos = new ObjectOutputStream(new FileOutputStream(Constant.BOOK_DATA_FILE));
            oos.writeObject(bookList_read);
            System.out.println("����ͼ��!");
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
