package com.bjpowernode.module.user;

import com.bjpowernode.bean.Constant;
import com.bjpowernode.bean.User;
import com.bjpowernode.global.util.Alerts;
import com.bjpowernode.service.UserService;
import com.bjpowernode.service.impl.UserServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserChargeViewCtrl {

    @FXML
    private TextField moneyField;

    private Stage stage;

    private User user;

    private TableView<User> userTableView;

    private UserService userServiceImpl = new UserServiceImpl();

    /*
        ��ֵ
     */
    @FXML
    private void charge() {
        try {
            //��ȡ��ֵ֮ǰ�����
            BigDecimal before = user.getMoney();
            //���γ�ֵ�Ľ��
            BigDecimal money = new BigDecimal(moneyField.getText());
            BigDecimal after = before.add(money);

            //�����ֵ֮������Ƿ����0
            if (after.compareTo(BigDecimal.ZERO) >= 0) {
                //�޸��û�״̬
                user.setStatus(Constant.USER_OK);
            }

            //����ֵ�����Ϣ�����û������ļ�
            user.setMoney(after);
            userServiceImpl.modify(user);
            System.out.println("�˴γ�ֵ���û���Ϣ��\t\t");
            User.printUsers(user);

            userTableView.refresh();
            stage.close();
            Alerts.success("�ɹ�", "�����ɹ�");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("ʧ��", "����ʧ��");
        }

    }

    @FXML
    private void closeView() {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public TableView<User> getUserTableView() {
        return userTableView;
    }

    public void setUserTableView(TableView<User> userTableView) {
        this.userTableView = userTableView;
    }
}
