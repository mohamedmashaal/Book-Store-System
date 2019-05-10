package controller;

import javafx.event.ActionEvent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Author;
import model.Book;
import model.DataManager;
import model.Publisher;

public class AddBookFormController {

    public TextField isbnTextField;
    public TextField titleTextField;
    public ChoiceBox categoryChoice;
    public TextField yearTextField;
    public TextField priceTextField;
    public TextField availQtyTextField;
    public TextField thresholdTextField;
    public Label addErrorLabel;
    public TextField authorsTextField;
    public TextField publisherNameTextField;
    public TextField publisherAddressTextField;
    public TextField publisherPhoneTextField;

    public void addBtnClicked(ActionEvent actionEvent) {
        System.out.println("Category: " + categoryChoice.getValue().toString());
        Book book = new Book(isbnTextField.getText(), titleTextField.getText(), categoryChoice.getValue().toString()
        , yearTextField.getText(), priceTextField.getText(), availQtyTextField.getText()
        , thresholdTextField.getText(), publisherNameTextField.getText());

        Publisher publisher = new Publisher(publisherNameTextField.getText(),
                publisherAddressTextField.getText(), publisherPhoneTextField.getText());

        if(DataManager.getInstance().insertPublisher(publisher))
            System.out.println("Publisher inserted!");

        if(DataManager.getInstance().insertBook(book)) {
            System.out.println("Book Inserted!");
            String[] authorNames = authorsTextField.getText().split(",");
            for(String authorName : authorNames){
                System.out.println("Author: " + authorName);
                Author author = new Author(authorName);
                if(DataManager.getInstance().insertAuthor(author, book.getIsbn()))
                    System.out.println("Author Inserted! " + authorName);
            }
        }
        else
            addErrorLabel.setText("Book cannot be added!");
    }
}
