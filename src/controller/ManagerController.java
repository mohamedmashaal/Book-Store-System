package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.AccountManager;

import java.util.Objects;

public class ManagerController {
    @FXML
    private Label welcomeLabel;


    public void addBookClicked() {
        Parent root;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/addBookForm.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Add Book Form");
            stage.setScene(new Scene(root));
            stage.show();
            //controlPanelButton.getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch add book form Screen");
        }
    }

    public void editBookClicked(ActionEvent actionEvent) {
        Parent root;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/editBookForm.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Edit Book Form");
            stage.setScene(new Scene(root));
            stage.show();
            //controlPanelButton.getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch edit book form Screen");
        }
    }

    public void promoteUserClicked(ActionEvent actionEvent) {
        Parent root;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/promoteUserForm.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Promote User Form");
            stage.setScene(new Scene(root));
            stage.show();
            //controlPanelButton.getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch promote user form Screen");
        }
    }

    public void placeOrderBtnClikced() {
        Parent root;

        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("views/orderPlaceForm.fxml")));
            Stage stage = new Stage();
            stage.setTitle("Place Order Form");
            stage.setScene(new Scene(root));
            stage.show();
            //controlPanelButton.getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch place order form Screen");
        }
    }
}
