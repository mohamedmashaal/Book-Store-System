package controller;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Book;

import java.util.ArrayList;
import java.util.HashMap;

public class CardController {
    private VBox card;
    private int quantity;
    private  ArrayList<String> selectedRow;
    private Book book;

    static HashMap<Book, Integer> bookQuantityHashMap = new HashMap<>();
    static HashMap<Book, HBox> bookHBoxHashMap = new HashMap<>();
    static HashMap<Button, Book> increaseBookHashMap = new HashMap<>();
    static HashMap<Button, Book> decreaseBookHashMap = new HashMap<>();
    static HashMap<Button, Book> removeBookHashMap = new HashMap<>();
    static HashMap<String, Book> isbnBookHashMap = new HashMap<>();
    static Label TotalPriceFiels;
    static double totalPruschase = 0;

    public CardController(final VBox cart, final String quantity, final ArrayList<String> selectedRow, Label prices) {
        this.card = cart;
        this.quantity = Integer.valueOf(quantity);
        this.selectedRow = selectedRow;
        book = new Book(selectedRow);
        TotalPriceFiels = prices;
        bookQuantityHashMap.put(book, this.quantity);
    }


    public void addToCard() {

        if(isbnBookHashMap.containsKey(book.getIsbn())){
            book = isbnBookHashMap.get(book.getIsbn());
            totalPruschase -= Double.valueOf(book.getSellingPrice()) * bookQuantityHashMap.get(book);
            ((Label)(bookHBoxHashMap.get(book).getChildren().get(5))).setText(String.valueOf(this.quantity));
            ((Label)(bookHBoxHashMap.get(book).getChildren().get(7))).setText(String.valueOf(Double.valueOf(book.getSellingPrice()) * this.quantity));
            bookQuantityHashMap.put(book, this.quantity);
            totalPruschase += Double.valueOf(book.getSellingPrice()) * bookQuantityHashMap.get(book);

            TotalPriceFiels.setText("Total Price:  " + totalPruschase);

            if(this.quantity <= 0){
                removeOrder(book);
            }
            return;
        }

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
        totalPruschase += Double.valueOf(book.getSellingPrice()) * this.quantity;

        hbox.getChildren().add(bookNumber);
        hbox.getChildren().add(bookTitle);
        hbox.getChildren().add(quantityString);
        hbox.getChildren().add(bookPirce);
        hbox.getChildren().add(totalPrice);

        increaseBookHashMap.put(increase, book);
        decreaseBookHashMap.put(decrease, book);
        removeBookHashMap.put(remove, book);
        bookHBoxHashMap.put(book, hbox);
        increase.setOnAction(e -> increaseOrder(increaseBookHashMap.get(increase)));
        decrease.setOnAction(e -> decreaseOrder(decreaseBookHashMap.get(decrease)));
        remove.setOnAction(e -> removeOrder(removeBookHashMap.get(remove)));
        card.getChildren().add(hbox);
        isbnBookHashMap.put(book.getIsbn(), book);
        TotalPriceFiels.setText("Total Price:  " + totalPruschase);

        if(Double.valueOf(quantityString.getText()) <= 0){
            removeOrder(book);
        }
    }


    private void removeOrder(Book book) {
        for(int i = 0 ; i < card.getChildren().size(); i++){
            if(bookHBoxHashMap.get(book) == card.getChildren().get(i)){
                card.getChildren().remove(bookHBoxHashMap.get(book));
                break;
            }
        }
        totalPruschase -= Double.valueOf(book.getSellingPrice()) * bookQuantityHashMap.get(book);
        TotalPriceFiels.setText("Total Price:  " + totalPruschase);
        bookHBoxHashMap.remove(book);
        bookQuantityHashMap.remove(book);
        isbnBookHashMap.remove(book.getIsbn());
    }


    private void increaseOrder(Book book) {
        bookQuantityHashMap.put(book, bookQuantityHashMap.get(book) + 1);
        HBox hBox = bookHBoxHashMap.get(book);
        Label quantityString  = (Label) hBox.getChildren().get(5);
        quantityString.setText(String.valueOf(Integer.valueOf(quantityString.getText()) + 1));
        Label totalPrice  = (Label) hBox.getChildren().get(7);
        totalPruschase += Double.valueOf(book.getSellingPrice());
        totalPrice.setText(String.valueOf(Double.valueOf(book.getSellingPrice()) * bookQuantityHashMap.get(book)));
        TotalPriceFiels.setText("Total Price:  " + totalPruschase);

    }


    private void decreaseOrder(Book book) {
        bookQuantityHashMap.put(book, bookQuantityHashMap.get(book) - 1);
        HBox hBox = bookHBoxHashMap.get(book);
        Label quantityString  = (Label) hBox.getChildren().get(5);
        quantityString.setText(String.valueOf(Integer.valueOf(quantityString.getText()) - 1));
        Label totalPrice  = (Label) hBox.getChildren().get(7);
        totalPrice.setText(String.valueOf(Double.valueOf(book.getSellingPrice()) * bookQuantityHashMap.get(book)));
        totalPruschase -= Double.valueOf(book.getSellingPrice());
        TotalPriceFiels.setText("Total Price:  " + totalPruschase);
        if(Double.valueOf(quantityString.getText()) <= 0){
            removeOrder(book);
        }
    }
}
