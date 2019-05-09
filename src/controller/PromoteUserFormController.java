package controller;

import contracts.DBContract;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.DataManager;

import java.util.HashMap;
import java.util.Map;

public class PromoteUserFormController {

    public TextField userNameTextField;
    public Label firstNameLabel;
    public Label lastNameLabel;


    public void promoteBtnClicked() {
        DataManager.getInstance().promoteUser(userNameTextField.getText());
    }

    public void searchForUser(ActionEvent actionEvent) {
        Map<String, String> user = DataManager.getInstance().getUser(userNameTextField.getText());
        firstNameLabel.setText(user.get(DBContract.User.FIRST_NAME_COLUMN));
        lastNameLabel.setText(user.get(DBContract.User.LAST_NAME_COLUMN));
    }
}
