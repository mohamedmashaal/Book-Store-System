package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.AccountManager;

public class customerController {
    @FXML
    private Label testLabel;

    @FXML
    private void initialize() {
        if(AccountManager.getManager().isThereActiveUser())
        testLabel.setText("Welcome .... " + AccountManager.getManager().getCurrentUser().getUserName());
    }
}
