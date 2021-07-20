package com.bjpowernode.module.charts;

import com.bjpowernode.service.BookService;
import com.bjpowernode.service.impl.BookServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author admin
 */
public class PieChart implements Initializable {

    @FXML
    private javafx.scene.chart.PieChart pieChart;

    private BookService bookServiceImpl = new BookServiceImpl();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//                new javafx.scene.chart.PieChart.Data("计算机", 20),
//                new javafx.scene.chart.PieChart.Data("文学", 12),
//                new javafx.scene.chart.PieChart.Data("经济", 25),
//                new javafx.scene.chart.PieChart.Data("管理", 22)


        List<javafx.scene.chart.PieChart.Data> dataList = bookServiceImpl.countBooks();
        System.out.println("统计结果：\t\n" + dataList);
        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList(dataList);

        pieChart.setData(pieChartData);
        pieChart.setClockwise(true);
    }
}
