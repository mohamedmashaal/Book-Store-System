package controller;

import contracts.Colors;
import contracts.Screens;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.DataManager;
import utils.SecurityManager;

public class loginController {
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;

    private String userName;
    private String password;

    public void initialize(){

    }

    @FXML
    void loginButtonClicked(ActionEvent event){
        resetErrorLabel();
        if(checkNonEmptyFields()){
            fillFields();
            DataManager manager = DataManager.getInstance();
            if(manager.searchForUser(userName)){
                String hashedPassword = manager.getUserHashedPassword(userName);
                String currentEnteredPasswordHash = SecurityManager.getInstance().getHashedPassword(password);
                if(hashedPassword.equals(currentEnteredPasswordHash)){
                    setErrorLabel("Login in Successful");
                }
                else{
                    setErrorLabel("Wrong Password");
                }
            }
            else{
                setErrorLabel("User not found");
            }
        }
    }

    private void setErrorLabel(String error){
        errorLabel.setText(error);
        errorLabel.setTextFill(Color.web(Colors.ERROR_RED));
    }

    private void fillFields() {
        userName = userNameField.getText();
        password = passwordField.getText();
    }

    private boolean checkNonEmptyFields() {
        if(userNameField.getText() == null || userNameField.getText() == ""){
            return false;
        }
        else if(passwordField.getText() == null || passwordField.getText() == ""){
            return false;
        }
        return true;
    }

    @FXML
    void cancelButtonClicked(ActionEvent event){
        getBack();
    }
    private void getBack(){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/startScreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle(Screens.START_SCREEN);
            stage.setScene(new Scene(root));
            stage.show();
            cancelButton.getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't return from the login window.");
        }
    }

    private void resetErrorLabel(){
        errorLabel.setText("");
    }
}
