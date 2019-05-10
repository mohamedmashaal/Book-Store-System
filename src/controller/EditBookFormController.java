package controller;

import contracts.DBContract;
import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Author;
import model.Book;
import model.DataManager;
import model.Publisher;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EditBookFormController {
    public TextField yearTextField;
    public TextField priceTextField;
    public TextField availQtyTextField;
    public TextField thresholdTextField;
    public Label addErrorLabel; // TODO:: Handle
    public TextField authorsTextField;
    public TextField publisherNameTextField;
    public TextField publisherAddressTextField;
    public TextField publisherPhoneTextField;
    public TextField searchTextField;
    public TextField isbnTextField;
    public TextField titleTextField;
    public ChoiceBox categoryChoice;
    public Label editErrorLabel;

    public void deleteBtnClicked() {
        String isbn = isbnTextField.getText();
        if(DataManager.getInstance().deleteBook(isbn))
            System.out.println("Book Deleted!");
    }

    public void editBtnClicked() {
        String isbn = isbnTextField.getText();

        //delete authors
        DataManager.getInstance().deleteAuthors(searchTextField.getText());

        //update publisher
        Publisher publisher = new Publisher(publisherNameTextField.getText(),
                publisherAddressTextField.getText(), publisherPhoneTextField.getText());

        if(DataManager.getInstance().insertPublisher(publisher))
            System.out.println("Publisher inserted!");

        //update book
        /*
        DataManager.getInstance().updateBookISBN(isbnTextField.getText(), searchTextField.getText());
        DataManager.getInstance().updateBookTitle(titleTextField.getText(), isbn);
        DataManager.getInstance().updateBookYear(yearTextField.getText(), isbn);
        DataManager.getInstance().updateBookCategory(categoryChoice.getValue().toString(), isbn);
        DataManager.getInstance().updateBookPrice(priceTextField.getText(), isbn);
        DataManager.getInstance().updateBookAvail(availQtyTextField.getText(), isbn);
        DataManager.getInstance().updateBookThreshold(thresholdTextField.getText(), isbn);
        DataManager.getInstance().updateBookPublisherName(publisherNameTextField.getText(), isbn);
        */
        Book book = new Book(isbnTextField.getText(), titleTextField.getText(), categoryChoice.getValue().toString(),
                yearTextField.getText(), priceTextField.getText(), availQtyTextField.getText(),
                thresholdTextField.getText(), publisherNameTextField.getText());
        if(DataManager.getInstance().updateBook(book, searchTextField.getText())){
            //update authors
            String[] authorNames = authorsTextField.getText().split(",");
            for(String authorName : authorNames){
                Author author = new Author(authorName);
                if(DataManager.getInstance().insertAuthor(author, isbn))
                    System.out.println("Author Inserted! " + authorName);
            }
        }
        else{
            editErrorLabel.setText("Book cannot be edited!");
        }
    }

    public void searchBtnClicked() throws SQLException {

        isbnTextField.setText("");
        titleTextField.setText("");
        yearTextField.setText("");
        categoryChoice.setValue("Science");
        priceTextField.setText("");
        availQtyTextField.setText("");
        thresholdTextField.setText("");
        publisherNameTextField.setText("");
        authorsTextField.setText("");
        publisherNameTextField.setText("");
        publisherAddressTextField.setText("");
        publisherPhoneTextField.setText("");

        ResultSet bookResultSet = DataManager.getInstance().getBook(searchTextField.getText());
        for(int i=1; i<=bookResultSet.getMetaData().getColumnCount(); i++){
            System.out.println(bookResultSet.getMetaData().getColumnName(i));
        }
        if(bookResultSet.next()){
            Book book = new Book(bookResultSet.getString(1), bookResultSet.getString(2),
                    bookResultSet.getString(3), bookResultSet.getString(4),
                    bookResultSet.getString(5), bookResultSet.getString(6),
                    bookResultSet.getString(7), bookResultSet.getString(8));
            System.out.println(book.toString());

            isbnTextField.setText(book.getIsbn());
            titleTextField.setText(book.getTitle());
            yearTextField.setText(book.getYear().split("-")[0]);
            categoryChoice.setValue(book.getCategory());
            priceTextField.setText(book.getSellingPrice());
            availQtyTextField.setText(book.getAvailQuantity());
            thresholdTextField.setText(book.getThreshold());
            publisherNameTextField.setText(book.getPublisherName());

            //authors info
            ResultSet authorResultSet = DataManager.getInstance().getAuthors(book.getIsbn());
            String authors = "";
            if(authorResultSet.next())
                authors += authorResultSet.getString(2);
            while(authorResultSet.next()) {
                authors += "," + authorResultSet.getString(2);
            }
            authorsTextField.setText(authors);

            //publisher info
            ResultSet publisherResultSet = DataManager.getInstance().getPublisher(book.getPublisherName());
            if(publisherResultSet.next()) {
                publisherNameTextField.setText(publisherResultSet.getString(1));
                publisherAddressTextField.setText(publisherResultSet.getString(2));
                publisherPhoneTextField.setText(publisherResultSet.getString(3));
            }
        }
    }
}
