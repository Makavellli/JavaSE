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
 * ͼ�����
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
        //��ȡͼ������
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
        ��ѯ
     */
    @FXML
    private void lendSelect() {
        String lendName = lendNameField.getText();
        String isbn = isbnField.getText();
        boolean lendFlag = "".equals(lendName);
        boolean isbnFlag = "".equals(isbn);
        List<Lend> lendList;
        if (lendFlag && isbnFlag) {
            System.out.println("��ѯ���н��ļ�¼...");
            System.out.println(lendTableView.getItems());
            lendTableView.getItems().clear();
            List<Lend> lendListQureyAll = lendServiceImpl.selectLends();
            lendTableView.setItems(FXCollections.observableArrayList(lendListQureyAll));
            lendTableView.refresh();
        } else {
            System.out.println("��ʼ��ѯ���ļ�¼...");
            lendList = lendServiceImpl.query(lendName, isbn);
            lendTableView.getItems().clear();
            lendTableView.setItems(FXCollections.observableArrayList(lendList));
            lendTableView.refresh();
        }
    }

    /*
        ����
     */
    @FXML
    private void returnBook() {
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null) {
            Alerts.warning("δѡ��", "����ѡ��Ҫ�黹���鼮");
            return;
        }
        System.out.println("���ļ�¼ID�ǣ�" + lend.getId());
        System.out.println("����ͼ���ǣ�" + lend);
        /**
         * ����ID���ҵ����ļ�¼���鼮���û�
         *      ���жϽ������ڵ���ǰ�����Ƿ����30��
         *          ��С�ڵ���30�� �����ֱ�ӻ���
         *          ������30�� ����Ҫ�Ƚ�����ã�ÿ����һ�찴1Ԫ�շ� �÷�����Ҫ���û������������п۳�
         *              ���۳��������ڵ���0 ��۳���ֱ�ӻ���
         *              ���۳������С��0, ����Ҫ�ȳ�ֵ
         *      ��������Ĳ�����
         *          ���½��ļ�¼�Ĺ黹����Ϊ��ǰ����
         *          �����鼮��״̬Ϊ ����⡱
         *          �����û��Ľ���״̬Ϊ ��δ���ġ�
         *
         */

//        lendServiceImpl
        //��ǰ���
        BigDecimal balance = userServiceImpl.getUserNameById(lend.getUserID()).getMoney();
        //���ڽ��
        BigDecimal outMoney = new BigDecimal(0);
        //�ж�ʱ��
        LocalDate returnDate = lend.getReturnDate();
        LocalDate now = LocalDate.now();
        Period between = Period.between(returnDate, now);

        //�������û�
        User readyToUpdateUser = new User();
        //������ͼ��
        Book readyToUpdateBook = new Book();
        //�������
        int days = between.getDays();
        System.out.println("���������" + days);
        if (now.isAfter(returnDate)) {
            //�����ǰʱ����ԭ�黹ʱ��֮����Ҫ����Ԥ�ڷ���
            outMoney = BigDecimal.valueOf(days);
            if (outMoney.intValue() >= 30) {
                outMoney = new BigDecimal(30);
            }
            System.out.println("���ڷ���Ϊ:" + outMoney);
            //ִ�пۿ����

            if (balance.compareTo(outMoney) == -1) {
                Alerts.warning("��ֵ", "���ڻ���ʱ������,�����Ԥ�ڷ��ù��ƣ�" + outMoney + "Ԫ����ǰ����ǣ�" + balance + ",���ֵ���飡");
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
            Alerts.success("�ɹ�", "��ϲ��,����ɹ�����ǰ����ǣ�" + balance.subtract(outMoney));
        }

        readyToUpdateUser = userServiceImpl.getUserNameById(lend.getUserID());
        readyToUpdateUser.setMoney(balance.subtract(outMoney));
        readyToUpdateUser.setLend(Constant.USER_LEND_NO);
        readyToUpdateBook = bookServiceImpl.getBookNameById(lend.getBookId());
        readyToUpdateBook.setStatus(Constant.STATUS_STORAGE);
        lend.setReturnDate(LocalDate.now());
        lend.setStatus(Constant.LEND_RETURN);
        lendServiceImpl.returnBook(lend, readyToUpdateUser, readyToUpdateBook);
        Alerts.success("�ɹ�", "��ϲ��,����ɹ�����ǰ����ǣ�" + balance.subtract(outMoney));
        lendTableView.refresh();
    }

    /*
        ����
     */
    @FXML
    private void renew() {
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null) {
            Alerts.warning("δѡ��", "����ѡ��Ҫ������鼮");
            return;
        }
        lend.setReturnDate(LocalDate.now().plusDays(30));
        lendServiceImpl.modify(lend);
        Alerts.success("�ɹ�", "����ɹ�,���µĻ�������Ϊ:" + lend.getReturnDate());
        lendTableView.refresh();
    }


    /*
        ��ʼ��stage
     */
    private void initStage(Lend lend) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/bjpowernode/module/lend/LendHandleView.fxml"));
        StackPane target = (StackPane) loader.load();
        //Scene scene1 = App.getDecorator().getScene();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//������̨��
        LendHandleViewCtrl controller = (LendHandleViewCtrl) loader.getController();
        controller.setStage(stage);
        controller.setLends(lends);
        controller.setLend(lend);
        controller.setLendTableView(lendTableView);
//        stage.setResizable(false);
        stage.setHeight(700);
        stage.setWidth(600);
        //���ô���ͼ��
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //������������̨��
        stage.show(); //��ʾ���ڣ�
    }
}
