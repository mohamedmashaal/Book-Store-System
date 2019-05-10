package controller;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.DataManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TopSellingBooksController {
    public TableView topBooksTable;

    @FXML
    public void initialize() throws SQLException {
        topBooksTable.getItems().clear();
        topBooksTable.getColumns().clear();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.add(Calendar.MONTH, -3);
        String dateThreeMonthsBefore = sdf.format(c.getTime());

        ResultSet ordersResultSet = DataManager.getInstance().getTopSellingBooks(dateThreeMonthsBefore);
        int n = ordersResultSet.getMetaData().getColumnCount();

        for(int i=0; i<n; i++){
            TableColumn<List<String>, String> column = new TableColumn<>(ordersResultSet.getMetaData().getColumnName(i+1));
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
            topBooksTable.getColumns().add(column);
        }

        while(ordersResultSet.next()){
            ArrayList<String> row = new ArrayList<>();
            for(int i=1; i<=n; i++){
                row.add(ordersResultSet.getString(i));
            }
            System.out.println("Row: " + row);
            topBooksTable.getItems().add(row);
        }
    }
}
