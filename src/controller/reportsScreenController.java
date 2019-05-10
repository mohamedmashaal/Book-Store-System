package controller;

import contracts.Screens;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Objects;

public class reportsScreenController {
    @FXML
    Button totalSales;

    private void startReportsScreen(){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/reportsScreen.fxml")));
            Stage stage = new Stage();
            stage.setTitle(Screens.REPORTS_SCREEN);
            stage.setScene(new Scene(root));
            stage.show();
            totalSales.getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch Reports Screen");
        }
    }

    public void totalSales(ActionEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/totalSalesReport.fxml")));
            Stage stage = new Stage();
            stage.setTitle(Screens.TOTAL_SALES_SCREEN);
            stage.setScene(new Scene(root));
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    startReportsScreen();
                }
            });
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch Total Sales Screen");
        }
    }

    public void topCustomers(ActionEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/topCustomersReport.fxml")));
            Stage stage = new Stage();
            stage.setTitle(Screens.TOP_CUSTOMERS_SCREEN);
            stage.setScene(new Scene(root));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            exception.printStackTrace();
            System.out.println("Couldn't launch Top Customers Screen");
        }
    }

    public void topSellingBooks(ActionEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/topSellingBooksReport.fxml")));
            Stage stage = new Stage();
            stage.setTitle(Screens.TOP_SELLING_BOOKS_SCREEN);
            stage.setScene(new Scene(root));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch Top Selling Books Screen");
        }
    }

}
