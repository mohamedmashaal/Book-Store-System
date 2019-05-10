package controller;

import javafx.beans.property.*;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;
import model.DataManager;

import java.util.ArrayList;

public class TopCustomersController {
    @FXML
    TableView<User> usersTable;
    @FXML
    TableColumn<User, String> userColumn;
    @FXML
    TableColumn<User, Integer> booksColumn;

    @FXML
    private void initialize() {
        userColumn.setCellValueFactory(new PropertyValueFactory("userName"));
        booksColumn.setCellValueFactory(new PropertyValueFactory("books"));
        DataManager manager = DataManager.getInstance();
        ArrayList<Pair<String, Integer>> users = manager.getTopFiveCustomers();
        for(Pair<String, Integer> user: users){
            User usr = new User();
            usr.setUserName(user.getKey());
            usr.setBooks(user.getValue());
            usersTable.getItems().add(usr);
        }
    }


    public class User{
        private StringProperty userName;
        public void setUserName(String value) { firstNameProperty().set(value); }
        public String getUserName() { return firstNameProperty().get(); }
        public StringProperty firstNameProperty() {
            if (userName == null) userName = new SimpleStringProperty(this, "userName");
            return userName;
        }

        private IntegerProperty books;
        public void setBooks(Integer value) { lastNameProperty().set(value); }
        public Integer getBooks() { return lastNameProperty().get(); }
        public IntegerProperty lastNameProperty() {
            if (books == null) books = new SimpleIntegerProperty(this, "books");
            return books;
        }

    }
}
