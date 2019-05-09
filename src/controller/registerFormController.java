package controller;

import contracts.Colors;
import contracts.Screens;
import contracts.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

public class registerFormController {
    @FXML
    private Button registerButton;
    @FXML
    private Button cancelButton;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField confirmPasswordField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private Label errorLabel;

    private String userName;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String hashedPassword;
    private String credential;

    @FXML
    private void initialize(){

    }

    @FXML
    void registerButtonClicked(ActionEvent event){
        resetErrorLabel();
        if(checkNonEmptyFields()){
            if(checkMatchingPassword()){
                if(!userExist()){
                    fillFields();
                    Customer customerTobeRegistered = getCustomer();
                    AccountManager accountManager = AccountManager.getManager();
                    boolean done = accountManager.register(customerTobeRegistered);
                    if(done){
                        getBack();
                    }
                    else{
                        setErrorLabel("Error While Registering Please Try Again ....");
                    }
                }
                else{
                    setErrorLabel("Username Already exists");
                }
            }
            else{
                setErrorLabel("Please check your password");
            }
        }
        else{
            setErrorLabel("Please fill up the missing fields");
        }
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setEmail(email);
        customer.setHashedPassword(hashedPassword);
        customer.setPhoneNumber(phone);
        customer.setUserName(userName);
        customer.setCredential(credential);
        customer.setAddress(address);
        return customer;
    }

    private void fillFields() {
        SecurityManager securityManager = SecurityManager.getInstance();
        userName = userNameField.getText();
        hashedPassword = securityManager.getHashedPassword(passwordField.getText());
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        email = emailField.getText();
        phone = phoneField.getText();
        address = addressField.getText();
        credential = Users.CUSTOMER;
    }

    private boolean userExist() {
        DataManager manager = DataManager.getInstance();
        return manager.searchForUser(userName);
    }

    private boolean checkMatchingPassword() {
        if(passwordField.getText().equals(confirmPasswordField.getText())){
            return true;
        }
        return false;
    }

    private boolean checkNonEmptyFields() {
        if(userNameField.getText() == null || userNameField.getText().equals("")){
            return false;
        }
        else if(passwordField.getText() == null || passwordField.getText().equals("")){
            return false;
        }
        else if(confirmPasswordField.getText() == null || confirmPasswordField.getText().equals("")){
            return false;
        }
        else if(firstNameField.getText() == null || firstNameField.getText().equals("")){
            return false;
        }
        else if(lastNameField.getText() == null || lastNameField.getText().equals("")){
            return false;
        }
        else if(emailField.getText() == null || emailField.getText().equals("")){
            return false;
        }
        else if(phoneField.getText() == null || phoneField.getText().equals("")){
            return false;
        }
        else if(addressField.getText() == null || addressField.getText().equals("")){
            return false;
        }
        return true;
    }

    private void resetErrorLabel(){
        errorLabel.setText("");
    }

    private void setErrorLabel(String error){
        errorLabel.setText(error);
        errorLabel.setTextFill(Color.web(Colors.ERROR_RED));
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
            System.out.println("Couldn't return from the registration window.");
        }
    }

}
