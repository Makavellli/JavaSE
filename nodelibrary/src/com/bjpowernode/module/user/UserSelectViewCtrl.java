package com.bjpowernode.module.user;

import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.global.util.Alerts;
import com.bjpowernode.module.book.BookLendViewCtrl;
import com.bjpowernode.service.UserService;
import com.bjpowernode.service.impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 用户查询
 *
 * @author admin
 */
public class UserSelectViewCtrl implements Initializable {

    @FXML
    private TableView<User> userSelectTableView;

    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableView<Lend> lendTableView;

    public TableView<User> getUserTableView() {
        return userTableView;
    }

    public void setUserTableView(TableView<User> userTableView) {
        this.userTableView = userTableView;
    }

    public TableView<Lend> getLendTableView() {
        return lendTableView;
    }

    public void setLendTableView(TableView<Lend> lendTableView) {
        this.lendTableView = lendTableView;
    }

    private TableView<Book> bookTableView;

    public TableView<Book> getBookTableView() {
        return bookTableView;
    }

    public void setBookTableView(TableView<Book> bookTableView) {
        this.bookTableView = bookTableView;
    }

    @FXML
    private TableColumn<User, String> c1;
    @FXML
    private TableColumn<User, String> c2;

    ObservableList<User> users = FXCollections.observableArrayList();

    private Stage stage;

    private BookLendViewCtrl bookLendViewCtrl;

    private UserService userService = new UserServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        List<User> userList = userService.getUserlist();
        List<User> qureyList;
        //过滤出当前无正在借阅状态的用户并返回
        Stream<User> stream = userList.stream().filter(user -> user.getLend().equals(Constant.USER_LEND_YES));
        if (stream != null) {
            qureyList = stream.collect(Collectors.toList());
            userList.removeAll(qureyList);
        }

        users.addAll(userList);
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        userSelectTableView.setItems(users);
    }

    @FXML
    private void userSelect() {
        User user = this.userSelectTableView.getSelectionModel().getSelectedItem();
        if (user == null) {
            Alerts.warning("未选择", "请先选择用户");
            return;
        }

        bookLendViewCtrl.setUser(user);
        bookLendViewCtrl.setBookTableView(bookTableView);
        stage.close();

    }

    @FXML
    private void closeView() {
        stage.close();
    }

    public BookLendViewCtrl getBookLendViewCtrl() {
        return bookLendViewCtrl;
    }

    public void setBookLendViewCtrl(BookLendViewCtrl bookLendViewCtrl) {
        this.bookLendViewCtrl = bookLendViewCtrl;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
