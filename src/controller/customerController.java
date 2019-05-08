package controller;

import contracts.Users;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
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

    ResultSet lastResultSet;

    @FXML
    private void initialize() throws SQLException {
        if(AccountManager.getManager().isThereActiveUser())
            testLabel.setText("Welcome .... " + AccountManager.getManager().getCurrentUser().getUserName());
        ResultSet resultSet = DataManager.getInstance().getAllBook();
        TitleField.setText("bad book");
        showDatainTableView(resultSet);
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
        int index = 0;
        ResultSet temp = lastResultSet;
        ArrayList<String> selectedRow = (ArrayList<String>) DataTable.getSelectionModel().getSelectedItem();
        CardController card = new CardController(this.Card, QuantityField.getText(), selectedRow);
        card.addToCard();
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
        CheckOutController checkOutController = new CheckOutController(CardController.bookQuantityHashMap, CreditNumberField.getText(), CVVField.getText(), ExpireDateField.getText());
        checkOutController.checkOut();
        testLabel.setText("Purchase Done");
    }
}
