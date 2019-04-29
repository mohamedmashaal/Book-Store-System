package controller;

import contracts.Colors;
import contracts.DBContract;
import contracts.Screens;
import contracts.Users;
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
import model.AccountManager;
import model.Customer;
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

    @FXML
    private void initialize(){

    }

    @FXML
    void loginButtonClicked(ActionEvent event){
        resetErrorLabel();
        if(checkNonEmptyFields()){
            fillFields();
            DataManager manager = DataManager.getInstance();
            if(manager.searchForUser(userName)){
                AccountManager accountManager = AccountManager.getManager();
                String hashedPassword = manager.getUserHashedPassword(userName);
                String currentEnteredPasswordHash = SecurityManager.getInstance().getHashedPassword(password);
                if(hashedPassword.equals(currentEnteredPasswordHash)){
                    Customer customer = accountManager.login(userName);
                    if(customer.getCredential().equalsIgnoreCase(Users.CUSTOMER)){
                        startScreen(Screens.CUSTOMER_SCREEN);
                    }
                    else if(customer.getCredential().equalsIgnoreCase(Users.MANAGER)){
                        startScreen(Screens.MANAGER_SCREEN);
                    }
                    else{
                        startScreen(Screens.MANAGER_SCREEN);
                    }
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

    private void startScreen(String screen) {
        Parent root;
        try {
            if(screen.equalsIgnoreCase(Screens.CUSTOMER_SCREEN)) {
                root = FXMLLoader.load(getClass().getClassLoader().getResource("views/customerScreen.fxml"));
            }
            else if(screen.equalsIgnoreCase(Screens.MANAGER_SCREEN)){
                root = FXMLLoader.load(getClass().getClassLoader().getResource("views/managerScreen.fxml"));
            }
            else{
                root = FXMLLoader.load(getClass().getClassLoader().getResource("views/managerScreen.fxml"));
            }
            Stage stage = new Stage();
            stage.setTitle(screen);
            stage.setScene(new Scene(root));
            stage.show();
            cancelButton.getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch customer or manager Screen");
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
