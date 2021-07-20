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
        充值
     */
    @FXML
    private void charge() {
        try {
            //获取充值之前的余额
            BigDecimal before = user.getMoney();
            //本次充值的金额
            BigDecimal money = new BigDecimal(moneyField.getText());
            BigDecimal after = before.add(money);

            //计算充值之后余额是否大于0
            if (after.compareTo(BigDecimal.ZERO) >= 0) {
                //修改用户状态
                user.setStatus(Constant.USER_OK);
            }

            //将充值后的信息更新用户数据文件
            user.setMoney(after);
            userServiceImpl.modify(user);
            System.out.println("此次充值的用户信息：\t\t");
            User.printUsers(user);

            userTableView.refresh();
            stage.close();
            Alerts.success("成功", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("失败", "操作失败");
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
