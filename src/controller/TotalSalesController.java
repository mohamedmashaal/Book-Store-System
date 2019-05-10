package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.DataManager;

public class TotalSalesController {
    @FXML
    Label totalSalesLabel;

    @FXML
    private void initialize() {
        DataManager manager = DataManager.getInstance();
        double totalSales = manager.getLastMonthTotalSales();
        totalSalesLabel.setText(Double.toString(totalSales));
    }

}
