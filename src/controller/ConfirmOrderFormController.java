package controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfirmOrderFormController {
    public TextField rowIndexTextField;
    public TableView ordersTable;

    @FXML
    public void initialize() throws SQLException {
        ordersTable.getItems().clear();
        ordersTable.getColumns().clear();
        ResultSet ordersResultSet = DataManager.getInstance().getJoinedOrders();
        int n = ordersResultSet.getMetaData().getColumnCount(), rowIndex = 0;

        TableColumn<List<String>, String> column1 = new TableColumn<>("Row Index");
        column1.setCellValueFactory(data -> {
            List<String> rowValues = data.getValue();
            String cellValue ;
            if (0 < rowValues.size()) {
                cellValue = rowValues.get(0);
            } else {
                cellValue = "";
            }
            return new ReadOnlyStringWrapper(cellValue);
        });

        ordersTable.getColumns().add(column1);
        for(int i=1; i<=n; i++){
            TableColumn<List<String>, String> column = new TableColumn<>(ordersResultSet.getMetaData().getColumnName(i));
            final int colIndex = i;
            column.setCellValueFactory(data -> {
                List<String> rowValues = data.getValue();
                String cellValue ;
                if (colIndex < rowValues.size()) {
                    cellValue = rowValues.get(colIndex);
                } else {
                    cellValue = "";
                }
                return new ReadOnlyStringWrapper(cellValue);
            });
            ordersTable.getColumns().add(column);
        }

        while(ordersResultSet.next()){
            rowIndex++;
            ArrayList<String> row = new ArrayList<>();
            row.add(Integer.toString(rowIndex));
            for(int i=1; i<=n; i++){
                row.add(ordersResultSet.getString(i));
            }
            System.out.println("Row: " + row);
            ordersTable.getItems().add(row);
        }
    }

    public void confirmOrderBtnClicked() {
        try{
            int rowIndex = Integer.parseInt(rowIndexTextField.getText());
            ArrayList<String> list = (ArrayList<String>) (ordersTable.getItems().get(rowIndex-1));
            System.out.println(list.toString());

            String id = list.get(1);
            String username = list.get(2);
            String isbn = list.get(4);
            String value = list.get(5);

            DataManager.getInstance().deleteOrderDetail(id, isbn);
            DataManager.getInstance().deleteOrder(id, username);

            DataManager.getInstance().addToAvailQuantity(isbn, value);

            initialize();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
