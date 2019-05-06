package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

public class CardController {
    private VBox card;
    private int quantity;
    private  ArrayList<String> selectedRow;
    private Book book;

    HashMap<Book, Integer> bookQuantity = new HashMap<>();
    HashMap<Book, HBox> bookHBoxHashMap = new HashMap<>();
    HashMap<Button, Book> increaseBook = new HashMap<>();
    HashMap<Button, Book> decreaseBook = new HashMap<>();
    HashMap<Button, Book> removeBook = new HashMap<>();


    public CardController(final VBox cart, final String quantity, final ArrayList<String> selectedRow) {
        this.card = cart;
        this.quantity = Integer.valueOf(quantity);
        this.selectedRow = selectedRow;
        book = new Book(selectedRow);
        bookQuantity.put(book, this.quantity);
    }


    public void checkOut() {

    }

    public void addToCard() {
        HBox hbox = new HBox();

        Button increase = new Button();
        Button decrease = new Button();
        Button remove = new Button();
        increase.setText("Increase");
        decrease.setText("Decrease");
        remove.setText("Remove");

        increase.getStyleClass().add("hbox");
        decrease.getStyleClass().add("hbox");
        remove.getStyleClass().add("hbox");

        hbox.getChildren().add(increase);
        hbox.getChildren().add(decrease);
        hbox.getChildren().add(remove);

        Label bookNumber = new Label();
        Label bookTitle = new Label();
        Label bookPirce = new Label();
        Label totalPrice = new Label();
        Label quantityString = new Label();

        bookNumber.getStyleClass().add("hbox");
        bookTitle.getStyleClass().add("hbox");
        bookPirce.getStyleClass().add("hbox");
        totalPrice.getStyleClass().add("hbox");
        quantityString.getStyleClass().add("hbox");


        bookNumber.setText(book.getIsbn());
        bookTitle.setText(book.getTitle());
        quantityString.setText(String.valueOf(this.quantity));
        bookPirce.setText(book.getSellingPrice());
        totalPrice.setText(String.valueOf(Double.valueOf(book.getSellingPrice()) * this.quantity));

        hbox.getChildren().add(bookNumber);
        hbox.getChildren().add(bookTitle);
        hbox.getChildren().add(quantityString);
        hbox.getChildren().add(bookPirce);
        hbox.getChildren().add(totalPrice);

        increaseBook.put(increase, book);
        decreaseBook.put(decrease, book);
        removeBook.put(remove, book);
        bookHBoxHashMap.put(book, hbox);
        increase.setOnAction(e -> increaseOrder(increaseBook.get(increase)));
        decrease.setOnAction(e -> decreaseOrder(decreaseBook.get(decrease)));
        remove.setOnAction(e -> removeOrder(removeBook.get(remove)));
        card.getChildren().add(hbox);
    }


    private void removeOrder(Book book) {
        for(int i = 0 ; i < card.getChildren().size(); i++){
            if(bookHBoxHashMap.get(book) == card.getChildren().get(i)){
                card.getChildren().remove(bookHBoxHashMap.get(book));
                break;
            }
        }
        bookHBoxHashMap.remove(book);
        bookQuantity.remove(book);
    }


    private void increaseOrder(Book book) {
        bookQuantity.put(book, bookQuantity.get(book) + 1);
        HBox hBox = bookHBoxHashMap.get(book);
        Label quantityString  = (Label) hBox.getChildren().get(5);
        quantityString.setText(String.valueOf(Double.valueOf(quantityString.getText()) + 1));
        Label totalPrice  = (Label) hBox.getChildren().get(7);
        totalPrice.setText(String.valueOf(Double.valueOf(book.getSellingPrice()) * Double.valueOf(quantityString.getText()) + 1));
    }


    private void decreaseOrder(Book book) {
        bookQuantity.put(book, bookQuantity.get(book) - 1);
        HBox hBox = bookHBoxHashMap.get(book);
        Label quantityString  = (Label) hBox.getChildren().get(5);
        quantityString.setText(String.valueOf(Double.valueOf(quantityString.getText()) - 1));
        Label totalPrice  = (Label) hBox.getChildren().get(7);
        totalPrice.setText(String.valueOf(Double.valueOf(book.getSellingPrice()) * Double.valueOf(quantityString.getText()) - 1));
    }
}
