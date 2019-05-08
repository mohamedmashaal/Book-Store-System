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

public class managerController {
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
}
