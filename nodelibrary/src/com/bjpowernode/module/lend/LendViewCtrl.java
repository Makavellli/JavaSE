package com.bjpowernode.module.lend;

import com.bjpowernode.service.BookService;
import com.bjpowernode.service.LendService;
import com.bjpowernode.service.UserService;
import com.bjpowernode.service.impl.BookServiceImpl;
import com.bjpowernode.service.impl.LendServiceImpl;
import com.bjpowernode.service.impl.UserServiceImpl;
import com.gn.App;
import com.bjpowernode.bean.Book;
import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.Lend;
import com.bjpowernode.bean.User;
import com.bjpowernode.global.util.Alerts;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 图书管理
 *
 * @author admin
 */
public class LendViewCtrl implements Initializable {

    @FXML
    private TableView<Lend> lendTableView;
    @FXML
    private TableColumn<Lend, String> c1;
    @FXML
    private TableColumn<Lend, String> c2;
    @FXML
    private TableColumn<Lend, String> c3;
    @FXML
    private TableColumn<Lend, String> c4;
    @FXML
    private TableColumn<Lend, String> c5;
    @FXML
    private TableColumn<Lend, String> c6;
    @FXML
    private TableColumn<Lend, String> c7;

    @FXML
    private TextField lendNameField;

    @FXML
    private TextField isbnField;

    ObservableList<Lend> lends = FXCollections.observableArrayList();

    private LendService lendServiceImpl = new LendServiceImpl();
    private UserService userServiceImpl = new UserServiceImpl();
    private BookService bookServiceImpl = new BookServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        lends.addAll(lendServiceImpl.selectLends());
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        //获取图书名称
        c2.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(bookServiceImpl.getBookNameById(p.getValue().getBookId()).getBookName())
        );
        c3.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(bookServiceImpl.getIsbnById(p.getValue().getBookId()).getIsbn())
        );
        PropertyValueFactory<Object, Object> userID = new PropertyValueFactory<>("userID");
//        c4.setCellValueFactory(new PropertyValueFactory<>("userID"));
        c4.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(userServiceImpl.getUserNameById(p.getValue().getUserID()).getName()));

//        (TableColumn.CellDataFeatures<Lend, String> p) ->
//                new SimpleObjectProperty(userServiceImpl.getUserNameById(p.getValue().getUserID()).getName())
        c5.setCellValueFactory(new PropertyValueFactory<>("lendDate"));
        c6.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        c7.setCellValueFactory(new PropertyValueFactory<>("status"));
        lendTableView.setItems(lends);

    }


    /*
        查询
     */
    @FXML
    private void lendSelect() {
        String lendName = lendNameField.getText();
        String isbn = isbnField.getText();
        boolean lendFlag = "".equals(lendName);
        boolean isbnFlag = "".equals(isbn);
        List<Lend> lendList;
        if (lendFlag && isbnFlag) {
            System.out.println("查询所有借阅记录...");
            System.out.println(lendTableView.getItems());
            lendTableView.getItems().clear();
            List<Lend> lendListQureyAll = lendServiceImpl.selectLends();
            lendTableView.setItems(FXCollections.observableArrayList(lendListQureyAll));
            lendTableView.refresh();
        } else {
            System.out.println("开始查询借阅记录...");
            lendList = lendServiceImpl.query(lendName, isbn);
            lendTableView.getItems().clear();
            lendTableView.setItems(FXCollections.observableArrayList(lendList));
            lendTableView.refresh();
        }
    }

    /*
        还书
     */
    @FXML
    private void returnBook() {
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null) {
            Alerts.warning("未选择", "请先选择要归还的书籍");
            return;
        }
        System.out.println("借阅记录ID是：" + lend.getId());
        System.out.println("借阅图书是：" + lend);
        /**
         * 根据ID查找到借阅记录、书籍、用户
         *      先判断借阅日期到当前日期是否大于30日
         *          若小于等于30日 则可以直接还书
         *          若大于30日 则需要先结算费用，每超出一天按1元收费 该费用需要从用户的余额里面进行扣除
         *              若扣除后余额大于等于0 则扣除后直接还书
         *              若扣除后余额小于0, 则需要先充值
         *      正常还书的操作：
         *          更新借阅记录的归还日期为当前日期
         *          更新书籍的状态为 “入库”
         *          更新用户的借阅状态为 “未借阅”
         *
         */

//        lendServiceImpl
        //当前余额
        BigDecimal balance = userServiceImpl.getUserNameById(lend.getUserID()).getMoney();
        //逾期金额
        BigDecimal outMoney = new BigDecimal(0);
        //判断时间
        LocalDate returnDate = lend.getReturnDate();
        LocalDate now = LocalDate.now();
        Period between = Period.between(returnDate, now);

        //待更新用户
        User readyToUpdateUser = new User();
        //待更新图书
        Book readyToUpdateBook = new Book();
        //相差天数
        int days = between.getDays();
        System.out.println("相差天数：" + days);
        if (now.isAfter(returnDate)) {
            //如果当前时间在原归还时间之后，需要计算预期费用
            outMoney = BigDecimal.valueOf(days);
            if (outMoney.intValue() >= 30) {
                outMoney = new BigDecimal(30);
            }
            System.out.println("逾期费用为:" + outMoney);
            //执行扣款操作

            if (balance.compareTo(outMoney) == -1) {
                Alerts.warning("充值", "由于还书时间逾期,需缴纳预期费用共计：" + outMoney + "元，当前余额是：" + balance + ",请充值后还书！");
                return;
            }
            readyToUpdateUser = userServiceImpl.getUserNameById(lend.getUserID());
            readyToUpdateUser.setMoney(balance.subtract(outMoney));
            readyToUpdateUser.setLend(Constant.USER_LEND_NO);
            readyToUpdateBook = bookServiceImpl.getBookNameById(lend.getBookId());
            readyToUpdateBook.setStatus(Constant.STATUS_STORAGE);
            lend.setReturnDate(LocalDate.now());
            lend.setStatus(Constant.LEND_RETURN);
            lendServiceImpl.returnBook(lend, readyToUpdateUser, readyToUpdateBook);
            Alerts.success("成功", "恭喜你,还书成功，当前余额是：" + balance.subtract(outMoney));
        }

        readyToUpdateUser = userServiceImpl.getUserNameById(lend.getUserID());
        readyToUpdateUser.setMoney(balance.subtract(outMoney));
        readyToUpdateUser.setLend(Constant.USER_LEND_NO);
        readyToUpdateBook = bookServiceImpl.getBookNameById(lend.getBookId());
        readyToUpdateBook.setStatus(Constant.STATUS_STORAGE);
        lend.setReturnDate(LocalDate.now());
        lend.setStatus(Constant.LEND_RETURN);
        lendServiceImpl.returnBook(lend, readyToUpdateUser, readyToUpdateBook);
        Alerts.success("成功", "恭喜你,还书成功，当前余额是：" + balance.subtract(outMoney));
        lendTableView.refresh();
    }

    /*
        续借
     */
    @FXML
    private void renew() {
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null) {
            Alerts.warning("未选择", "请先选择要续借的书籍");
            return;
        }
        lend.setReturnDate(LocalDate.now().plusDays(30));
        lendServiceImpl.modify(lend);
        Alerts.success("成功", "续借成功,最新的还书日期为:" + lend.getReturnDate());
        lendTableView.refresh();
    }


    /*
        初始化stage
     */
    private void initStage(Lend lend) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/bjpowernode/module/lend/LendHandleView.fxml"));
        StackPane target = (StackPane) loader.load();
        //Scene scene1 = App.getDecorator().getScene();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        LendHandleViewCtrl controller = (LendHandleViewCtrl) loader.getController();
        controller.setStage(stage);
        controller.setLends(lends);
        controller.setLend(lend);
        controller.setLendTableView(lendTableView);
//        stage.setResizable(false);
        stage.setHeight(700);
        stage.setWidth(600);
        //设置窗口图标
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台；
        stage.show(); //显示窗口；
    }
}
