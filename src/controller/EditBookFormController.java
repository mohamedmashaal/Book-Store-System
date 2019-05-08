package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Book;
import model.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EditBookFormController {
    public TextField yearTextField;
    public TextField priceTextField;
    public TextField availQtyTextField;
    public TextField thresholdTextField;
    public TextField defaultOrderTextField;
    public Label addErrorLabel;
    public TextField authorsTextField;
    public TextField publisherNameTextField;
    public TextField publisherAddressTextField;
    public TextField publisherPhoneTextField;
    public TextField searchTextField;
    public TextField isbnTextField;
    public TextField titleTextField;
    public ChoiceBox categoryChoice;

    public void editBtnClicked() {

    }

    public void deleteBtnClicked() {
    }

    public void searchBtnClicked() throws SQLException {
        ResultSet resultSet = DataManager.getInstance().getBook(searchTextField.getText());
        for(int i=1; i<=resultSet.getMetaData().getColumnCount(); i++){
            System.out.println(resultSet.getMetaData().getColumnName(i));
        }
        if(resultSet.next()){
            Book book = new Book(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getString(6),
                    resultSet.getString(7), resultSet.getString(8));
            System.out.println(book.toString());

            isbnTextField.setText(book.getIsbn());
            titleTextField.setText(book.getTitle());
            yearTextField.setText(book.getYear());
            categoryChoice.setValue(book.getCategory());
            priceTextField.setText(book.getSellingPrice());
            availQtyTextField.setText(book.getAvailQuantity());
            thresholdTextField.setText(book.getThreshold());
            defaultOrderTextField.setText(book.getDefaultOrder());
        }
    }
}
