package controller;

import contracts.Colors;
import contracts.Screens;
import contracts.Users;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.AccountManager;
import model.Customer;
import model.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class customerController {
    public TextField TitleField;
    public TextField CategoryField;
    public TextField AvailableQuantityField;
    public TextField SellingPriceField;
    public TextField PublicationYearField;
    public TextField ISBNField;
    public TextField AuthorNameField;
    public TextField PublisherName;
    public TextField ThresholdField;
    public TableView DataTable;
    public TextField QuantityField;
    public VBox Card;
    public Button CheckOutButton;
    public Button controlPanelButton;
    public Label controlPanelErrorLabel;
    public TextField CreditNumberField;
    public TextField CVVField;
    public TextField ExpireDateField;
    @FXML
    private Label testLabel;
    @FXML
    private Label errorLabelVisa;
    @FXML
    private Label errorLabelQuantity;

    ResultSet lastResultSet;

    @FXML
    private void initialize() throws SQLException {
        if(AccountManager.getManager().isThereActiveUser())
            testLabel.setText("Welcome .... " + AccountManager.getManager().getCurrentUser().getUserName());
        ResultSet resultSet = DataManager.getInstance().getAllBook();
        TitleField.setText("bad book");
        showDatainTableView(resultSet);
        ExpireDateField.setText("2006-01-00");

    }

    public void editSettings(ActionEvent event){
        Parent root;
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/userSettingsScreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle(Screens.USER_PROFILE_SETTINGS);
            stage.setScene(new Scene(root));
            stage.show();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch Profile edit window.");
        }
    }

    private void showDatainTableView(ResultSet resultSet) throws SQLException {
        lastResultSet = resultSet;
        DataTable.getItems().clear();
        DataTable.getColumns().clear();
        for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++) {
            TableColumn<List<String>, String> col = new TableColumn<>(resultSet.getMetaData().getColumnName(i+1));
          //  System.out.println(resultSet.getMetaData().getColumnName(i+1));
            final int colIndex = i ;
            col.setCellValueFactory(data -> {
                List<String> rowValues = data.getValue();
                String cellValue ;
                if (colIndex < rowValues.size()) {
                    cellValue = rowValues.get(colIndex);
                } else {
                    cellValue = "" ;
                }
                return new ReadOnlyStringWrapper(cellValue);
            });
            DataTable.getColumns().add(col);
        }
        while (resultSet.next()) {
            ArrayList<String> row = new ArrayList<>();
            for(int i = 0; i < resultSet.getMetaData().getColumnCount(); i++)
                row.add(resultSet.getString(i+1));
            DataTable.getItems().add(row);
        }
        Platform.runLater(() ->
        {
            DataTable.requestFocus();
            DataTable.getSelectionModel().select(0);
            DataTable.scrollTo(0);
        });
    }

    public void search(final ActionEvent actionEvent) throws SQLException {
        ArrayList<String> searchParametes = new ArrayList<>();

        searchParametes.add(ISBNField.getText());
        searchParametes.add(TitleField.getText());
        searchParametes.add(CategoryField.getText());
        searchParametes.add(PublicationYearField.getText());
        searchParametes.add(SellingPriceField.getText());
        searchParametes.add(AvailableQuantityField.getText());
        searchParametes.add(ThresholdField.getText());
        searchParametes.add(PublisherName.getText());
        searchParametes.add(AuthorNameField.getText());

        ResultSet resultSet = DataManager.getInstance().searchForBook(searchParametes);
        showDatainTableView(resultSet);
    }

    public void addToCart(final ActionEvent actionEvent) throws SQLException {
        resetErrorLabel(errorLabelQuantity);
        if(checkQuantity()) {
            int index = 0;
            ResultSet temp = lastResultSet;
            ArrayList<String> selectedRow = (ArrayList<String>) DataTable.getSelectionModel().getSelectedItem();
            CardController card = new CardController(this.Card, QuantityField.getText(), selectedRow);
            card.addToCard();
        }
        else{
            setErrorLabel("Check Quantity", errorLabelQuantity);
        }
    }

    private boolean checkQuantity(){
        if(QuantityField.getText() == null || QuantityField.getText().equals(""))
            return false;
        if(!QuantityField.getText().matches("[0-9]+"))
            return false;
        return true;
    }

    public void openControlPanel(){
        Customer customer = AccountManager.getManager().getCurrentUser();
        if(customer.getCredential().equalsIgnoreCase(Users.CUSTOMER)){
            controlPanelErrorLabel.setText("You don't have enough credentials");
        }
        else{
            controlPanelErrorLabel.setText("Welcome back!");
            startManagerScreen();
        }
    }

    private void startManagerScreen() {
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/managerScreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Manager's Control Panel");
            stage.setScene(new Scene(root));
            stage.show();
            //controlPanelButton.getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch manager Screen");
        }
    }

    public void CheckOut(final ActionEvent actionEvent) {
        resetErrorLabel(errorLabelVisa);
        if(nonEmpty() && followPattern()){
            CheckOutController checkOutController = new CheckOutController(CardController.bookQuantityHashMap, CreditNumberField.getText(), CVVField.getText(), getDate(ExpireDateField.getText()));
            checkOutController.checkOut();
            testLabel.setText("Purchase Done");
        }
        else{
            setErrorLabel("Check Credit Card Info", errorLabelVisa);
        }
    }

    private String getDate(String expiration){
        String date = new String("20XX-XX-00");
        for(int i = 0 ; i <expiration.length() ; i++){
            date = date.replaceFirst("X", Character.toString(expiration.charAt(i)));
        }
        return date;
    }

    private boolean nonEmpty() {
        if(CreditNumberField.getText() == null || CreditNumberField.getText().equals("")){
            return false;
        }
        else if(CVVField.getText() == null || CVVField.getText().equals("")){
            return false;
        }
        else if(ExpireDateField.getText() == null || ExpireDateField.getText().equals("")){
            return false;
        }
        return true;
    }

    private boolean followPattern() {
        if(nonEmpty()){
            if(haveAnythingButNumbers()) {
                if (CreditNumberField.getText().length() != 16)
                    return false;
                if (CVVField.getText().length() != 3 && CVVField.getText().length() != 4)
                    return false;
                if(ExpireDateField.getText().length() != 4)
                    return false;
                return true;
            }
        }
        return false;
    }

    private boolean haveAnythingButNumbers() {
        if(!CreditNumberField.getText().matches("[0-9]+"))
            return false;
        if(!CVVField.getText().matches("[0-9]+"))
            return false;
        if(!ExpireDateField.getText().matches("[0-9]+"))
            return false;
        return true;
    }

    public void logOut(ActionEvent actionEvent){
        AccountManager.getManager().logout();
        startStartScreen(actionEvent);
    }

    private void startStartScreen(ActionEvent event) {
        Parent root;

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("views/startScreen.fxml"));
            Stage stage = new Stage();
            stage.setTitle(Screens.START_SCREEN);
            stage.setScene(new Scene(root));
            stage.show();
            ((Node)(event.getSource())).getScene().getWindow().hide();
        }catch (java.io.IOException exception){
            System.out.println("Couldn't launch Start Screen");
        }
    }

    private void resetErrorLabel(Label label){
        label.setText("");
    }

    private void setErrorLabel(String error,Label label){
        label.setText(error);
        label.setTextFill(Color.web(Colors.ERROR_RED));
    }
}
