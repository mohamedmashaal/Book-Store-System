package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.AccountManager;
import model.DataManager;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderPlaceFormController {
    public TextField isbnTextField;
    public TextField quantityTextField;
    public Label notifLabel;


    public void orderBtnClicked() {
        String username = AccountManager.getManager().getCurrentUser().getUserName();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String orderTime = sdf.format(new Date());

        String isbn = isbnTextField.getText();
        String quantity = quantityTextField.getText();

        String orderID = DataManager.getInstance().insertOrder(username, orderTime);

        if(orderID != null){
            DataManager.getInstance().insertOrderDetail(orderID, isbn, quantity);
            notifLabel.setText("Success!");
        }
        else{
            notifLabel.setText("Failure!");
        }
    }
}
