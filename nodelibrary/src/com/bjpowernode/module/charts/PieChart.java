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
//                new javafx.scene.chart.PieChart.Data("�����", 20),
//                new javafx.scene.chart.PieChart.Data("��ѧ", 12),
//                new javafx.scene.chart.PieChart.Data("����", 25),
//                new javafx.scene.chart.PieChart.Data("����", 22)


        List<javafx.scene.chart.PieChart.Data> dataList = bookServiceImpl.countBooks();
        System.out.println("ͳ�ƽ����\t\n" + dataList);
        ObservableList<javafx.scene.chart.PieChart.Data> pieChartData = FXCollections.observableArrayList(dataList);

        pieChart.setData(pieChartData);
        pieChart.setClockwise(true);
    }
}
