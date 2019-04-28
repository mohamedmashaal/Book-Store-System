package controller;

import contracts.Screens;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class startController {
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label title;

    public void initialize(){

    }

    @FXML
    void loginButtonClicked(ActionEvent event){

    }

    @FXML
    void registerButtonClicked(ActionEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/registerForm.fxml"));
            Stage stage = new Stage();
            stage.setTitle(Screens.REGISTER_SCREEN);
            stage.setScene(new Scene(root));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch the registration window.");
        }
    }


}
