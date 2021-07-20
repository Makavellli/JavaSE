package com.bjpowernode.module.book;

import com.bjpowernode.bean.Lend;
import com.bjpowernode.global.util.Alerts;
import com.bjpowernode.service.BookService;
import com.bjpowernode.service.LendService;
import com.bjpowernode.service.UserService;
import com.bjpowernode.service.impl.BookServiceImpl;
import com.bjpowernode.service.impl.LendServiceImpl;
import com.bjpowernode.service.impl.UserServiceImpl;
import com.gn.App;
import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.User;
import com.bjpowernode.module.user.UserSelectViewCtrl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class BookLendViewCtrl {

    @FXML
    private TextField bookIdField;

    @FXML
    private TextField bookNameField;

    @FXML
    private TextField userIdField;

    @FXML
    private TextField userNameField;

    private Stage stage;

    //���ĵ���
    private Book book;

    //������
    private User user;

    @FXML
    private TableView<Book> bookTableView;

    public TableView<Book> getBookTableView() {
        return bookTableView;
    }

    public void setBookTableView(TableView<Book> bookTableView) {
        this.bookTableView = bookTableView;
    }

    private LendService lendServiceImpl = new LendServiceImpl();

    @FXML
    private void closeView() {
        stage.close();
    }

    @FXML
    private void add() {
        /**
         * ��ȡҳ���user��book��Ϣ ������װ���ļ�¼
         *      �û�����Ƿ����״̬����  true
         *      ͼ���Ľ���״̬����Ϊ  "����"
         */
        if (user == null) {
            Alerts.info("δѡ��", "��ѡ����Ҫ����ͼ����û���");
            return;
        }
        if (book == null) {
            Alerts.warning("δѡ��", "��ѡ����Ҫ���ĵ�ͼ�飡");
            return;
        }
        System.out.println("ҳ�洫�ݵ�ͼ��ID�ǣ�" + Integer.parseInt(bookIdField.getText()));
        System.out.println("ҳ�洫�ݵ��û�ID�ǣ�" + Integer.parseInt(userIdField.getText()));

        lendServiceImpl.add(Integer.parseInt(bookIdField.getText()), Integer.parseInt(userIdField.getText()));

        ObservableList<Book> books = bookTableView.getItems();
        System.out.println(books);
        System.out.println("ˢ��bookTableView---------");
        bookTableView.refresh();
        stage.close();

    }

    /*
        ��ʼ�������û�ѡ���stage
    */
    @FXML
    private void initSelectUserStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/bjpowernode/module/user/UserSelectView.fxml"));
        StackPane target = (StackPane) loader.load();
        Scene scene = new Scene(target);

        Stage stage = new Stage();//������̨��
        UserSelectViewCtrl controller = (UserSelectViewCtrl) loader.getController();
        controller.setStage(stage);
        controller.setBookLendViewCtrl(this);
        controller.setBookTableView(bookTableView);
        stage.setHeight(700);
        stage.setWidth(600);
        //���ô���ͼ��
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //������������̨��
        stage.show(); //��ʾ���ڣ�
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
        bookIdField.setText(String.valueOf(book.getId()));
        bookNameField.setText(book.getBookName());
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        userIdField.setText(String.valueOf(user.getId()));
        userNameField.setText(user.getName());
    }
}
