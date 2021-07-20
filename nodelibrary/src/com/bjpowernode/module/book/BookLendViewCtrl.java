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

    //借阅的书
    private Book book;

    //借阅者
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
         * 获取页面的user和book信息 处理组装借阅记录
         *      用户表的是否借阅状态更新  true
         *      图书表的借阅状态更新为  "出借"
         */
        if (user == null) {
            Alerts.info("未选择", "请选择需要借阅图书的用户！");
            return;
        }
        if (book == null) {
            Alerts.warning("未选择", "请选择需要借阅的图书！");
            return;
        }
        System.out.println("页面传递的图书ID是：" + Integer.parseInt(bookIdField.getText()));
        System.out.println("页面传递的用户ID是：" + Integer.parseInt(userIdField.getText()));

        lendServiceImpl.add(Integer.parseInt(bookIdField.getText()), Integer.parseInt(userIdField.getText()));

        ObservableList<Book> books = bookTableView.getItems();
        System.out.println(books);
        System.out.println("刷新bookTableView---------");
        bookTableView.refresh();
        stage.close();

    }

    /*
        初始化借阅用户选择的stage
    */
    @FXML
    private void initSelectUserStage() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/bjpowernode/module/user/UserSelectView.fxml"));
        StackPane target = (StackPane) loader.load();
        Scene scene = new Scene(target);

        Stage stage = new Stage();//创建舞台；
        UserSelectViewCtrl controller = (UserSelectViewCtrl) loader.getController();
        controller.setStage(stage);
        controller.setBookLendViewCtrl(this);
        controller.setBookTableView(bookTableView);
        stage.setHeight(700);
        stage.setWidth(600);
        //设置窗口图标
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台；
        stage.show(); //显示窗口；
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
