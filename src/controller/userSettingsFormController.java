package controller;

import contracts.Colors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AccountManager;
import model.Customer;
import model.DataManager;
import utils.SecurityManager;

import java.sql.SQLException;

public class userSettingsFormController {
    @FXML
    private TextField oldPasswordField;
    @FXML
    private TextField newPasswordField;
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
    private String credential;
    private String hashedPassword;

    @FXML
    private void initialize() throws SQLException {
        fillCurrentUserData();
        fillFields();
    }

    private void fillFields() {
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        emailField.setText(email);
        phoneField.setText(phone);
        addressField.setText(address);
    }

    private void fillCurrentUserData() {
        userName = AccountManager.getManager().getCurrentUser().getUserName();
        firstName = AccountManager.getManager().getCurrentUser().getFirstName();
        lastName = AccountManager.getManager().getCurrentUser().getLastName();
        email = AccountManager.getManager().getCurrentUser().getEmail();
        phone = AccountManager.getManager().getCurrentUser().getPhoneNumber();
        address = AccountManager.getManager().getCurrentUser().getAddress();
        hashedPassword = AccountManager.getManager().getCurrentUser().getHashedPassword();
        credential = AccountManager.getManager().getCurrentUser().getCredential();
    }

    public void saveDetails(ActionEvent event){
        resetErrorLabel();
        if(checkFields()){
            if(checkOldPassword()){
                if(hasNewPassword()){
                    if(checkMatchingNewPassword()){
                        DataManager manager = DataManager.getInstance();
                        hashedPassword = SecurityManager.getInstance().getHashedPassword(newPasswordField.getText());
                        getFields(true);
                        Customer customer = getCustomer();
                        boolean done = manager.updateCustomer(customer);
                        if(done){
                            AccountManager.getManager().setCurrentUser(customer);
                            closeScreen();
                        }
                        else{
                            setErrorLabel("There was an Error while updating your data, Please try again");
                        }
                    }
                    else{
                        setErrorLabel("Check new Password");
                    }
                }
                else{
                    getFields(false);
                    Customer customer = getCustomer();
                    boolean done = DataManager.getInstance().updateCustomer(customer);
                    if(done){
                        AccountManager.getManager().setCurrentUser(customer);
                        closeScreen();
                    }
                    else{
                        setErrorLabel("There was an Error while updating your data, Please try again");
                    }
                }
            }
            else{
                setErrorLabel("Please check Password Field");
            }
        }
        else{
            setErrorLabel("Please fill up the required fields");
        }
    }

    private void getFields(boolean withPassword) {
        firstName = firstNameField.getText();
        lastName = lastNameField.getText();
        phone = phoneField.getText();
        address = addressField.getText();
        email = emailField.getText();
        if(withPassword){
            hashedPassword = SecurityManager.getInstance().getHashedPassword(newPasswordField.getText());
        }
    }

    private void closeScreen() {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setFirstName(firstName);
        customer.setLastName(lastName);
        customer.setAddress(address);
        customer.setPhoneNumber(phone);
        customer.setUserName(userName);
        customer.setHashedPassword(hashedPassword);
        customer.setCredential(credential);
        customer.setEmail(email);
        return customer;
    }

    private boolean checkMatchingNewPassword() {
        String newPasswordHashed = SecurityManager.getInstance().getHashedPassword(newPasswordField.getText());
        String confirmedPasswordHashed = SecurityManager.getInstance().getHashedPassword(confirmPasswordField.getText());
        if(newPasswordHashed.equals(confirmedPasswordHashed))
            return true;
        return false;
    }

    private boolean hasNewPassword() {
        if(newPasswordField.getText() != null && !newPasswordField.getText().equals(""))
            return true;
        else if(confirmPasswordField.getText() != null && !confirmPasswordField.getText().equals(""))
            return true;
        return false;
    }

    private boolean checkOldPassword() {
        String enteredPassword = oldPasswordField.getText();
        if(hashedPassword.equals(SecurityManager.getInstance().getHashedPassword(enteredPassword))){
            return true;
        }
        return false;
    }

    private boolean checkFields() {
        if(oldPasswordField.getText() == null || oldPasswordField.getText().equals("")){
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
}
