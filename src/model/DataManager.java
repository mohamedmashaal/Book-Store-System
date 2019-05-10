package model;

import contracts.DBContract;
import contracts.SqlCommands;
import javafx.util.Pair;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DataManager {
    private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    private static DataManager uniqueInstance;

    private DataManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
            connect = DriverManager
                    .getConnection("jdbc:mysql://154.237.68.52:3306/" + DBContract.DB_NAME, DBContract.DB_USERNAME, DBContract.DB_PASSWORD);
            connect.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getException().getMessage());
        }
    }

    public static DataManager getInstance() {
        if (uniqueInstance == null) {
            return uniqueInstance = new DataManager();
        }
        return uniqueInstance;
    }

    public boolean deleteInstance() {
        uniqueInstance = null;
        return close();
    }

    private boolean close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (preparedStatement != null) {
                preparedStatement.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connect != null) {
                connect.close();
            }
            return true;
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());
            return false;
        }
    }

    public boolean searchForUser(String userName) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.SEARCH_USERNAME);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();
            ArrayList<String> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(resultSet.getString(DBContract.User.USER_NAME_COLUMN));
            }
            if (users.size() == 0) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return true;
        }
    }

    public boolean insertCustomer(Customer customer) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.INSERT_USER);
            preparedStatement.setString(1, customer.getUserName());
            preparedStatement.setString(2, customer.getHashedPassword());
            preparedStatement.setString(3, customer.getFirstName());
            preparedStatement.setString(4, customer.getLastName());
            preparedStatement.setString(5, customer.getPhoneNumber());
            preparedStatement.setString(6, customer.getAddress());
            preparedStatement.setString(7, customer.getCredential());
            preparedStatement.setString(8, customer.getEmail());
            int cnt = preparedStatement.executeUpdate();
            if (cnt == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getUserHashedPassword(String userName) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.GET_PASSWORD);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();
            String password = "";
            while (resultSet.next()) {
                password = resultSet.getString(DBContract.User.PASSWORD_COLUMN);
            }
            return password;
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }

    public HashMap<String, String> getUser(String userName) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.GET_USER);
            preparedStatement.setString(1, userName);
            resultSet = preparedStatement.executeQuery();
            HashMap<String, String> userData = new HashMap<>();
            while (resultSet.next()) {
                userData.put(DBContract.User.USER_NAME_COLUMN, resultSet.getString(DBContract.User.USER_NAME_COLUMN));
                userData.put(DBContract.User.PASSWORD_COLUMN, resultSet.getString(DBContract.User.PASSWORD_COLUMN));
                userData.put(DBContract.User.FIRST_NAME_COLUMN, resultSet.getString(DBContract.User.FIRST_NAME_COLUMN));
                userData.put(DBContract.User.LAST_NAME_COLUMN, resultSet.getString(DBContract.User.LAST_NAME_COLUMN));
                userData.put(DBContract.User.ADDRESS_COLUMN, resultSet.getString(DBContract.User.ADDRESS_COLUMN));
                userData.put(DBContract.User.PHONE_COLUMN, resultSet.getString(DBContract.User.PHONE_COLUMN));
                userData.put(DBContract.User.CREDENTIAL_COLUMN, resultSet.getString(DBContract.User.CREDENTIAL_COLUMN));
                userData.put(DBContract.User.EMAIL_COLUMN, resultSet.getString(DBContract.User.EMAIL_COLUMN));
            }
            return userData;
        } catch (SQLException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    public ResultSet getAllBook() {
        try {
            preparedStatement = connect.prepareStatement("select * from book");
            resultSet = preparedStatement.executeQuery();
            return resultSet;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet searchForBook(ArrayList<String> searchParameters) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.Search_For_Book);
            int index = 1;
            for (int i = 0; i < searchParameters.size(); i++) {
                preparedStatement.setString(index++, searchParameters.get(i));
                preparedStatement.setString(index++, searchParameters.get(i));
            }
            int x = 0;
            return preparedStatement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertBook(Book book) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.INSERT_BOOK);
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getCategory());
            preparedStatement.setString(4, book.getYear());
            preparedStatement.setString(5, book.getSellingPrice());
            preparedStatement.setString(6, book.getAvailQuantity());
            preparedStatement.setString(7, book.getThreshold());
            preparedStatement.setString(8, book.getPublisherName());
            int cnt  = preparedStatement.executeUpdate();
            if(cnt == 1){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertAuthor(Author author, String isbn) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.INSERT_AUTHOR);
            preparedStatement.setString(1, isbn);
            preparedStatement.setString(2, author.getName());
            int cnt = preparedStatement.executeUpdate();
            if (cnt == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertPublisher(Publisher publisher){
        try{
            preparedStatement = connect.prepareStatement(SqlCommands.INSERT_PUBLISHER);
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getAddress());
            preparedStatement.setString(3, publisher.getPhone());
            int cnt  = preparedStatement.executeUpdate();
            if(cnt == 1){
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public boolean insertPurchase(ArrayList<String> parameters, HashMap<Book, Integer> orders) {
        try {

            connect.setAutoCommit(false);
            PreparedStatement purchaseStatment = connect.prepareStatement(SqlCommands.INSERT_PURCHASE);
            PreparedStatement purchaseDetailsStatement = connect.prepareStatement(SqlCommands.INSERT_PURCHASE_DETAILS);
            for (int i = 0; i < parameters.size(); i++) {
                purchaseStatment.setString(i + 1, parameters.get(i));
            }
            purchaseStatment.executeUpdate();
            PreparedStatement lastId = connect.prepareStatement(SqlCommands.LAST_ID);
            ResultSet resultSet = lastId.executeQuery();
            resultSet.next();
            String id = resultSet.getString(1);

            for (Map.Entry<Book, Integer> e : orders.entrySet()) {
                purchaseDetailsStatement.setString(1, id);
                purchaseDetailsStatement.setString(2, e.getKey().getIsbn());
                purchaseDetailsStatement.setString(3, String.valueOf(e.getValue()));
                purchaseDetailsStatement.executeUpdate();
            }
            connect.commit();
        } catch (SQLException e) {
            try {
                System.err.print("Transaction is being rolled back");
                connect.rollback();
            } catch (SQLException excep) {
                return false;
            }
            return false;
        } finally {
            try {
                connect.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }


    public ResultSet getBook(String isbn){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.GET_BOOK);
            preparedStatement.setString(1, isbn);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getAuthors(String isbn){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.GET_AUTHORS);
            preparedStatement.setString(1, isbn);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ResultSet getPublisher(String name){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.GET_PUBLISHER);
            preparedStatement.setString(1, name);
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteAuthors(String isbn){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.DELETE_AUTHORS);
            preparedStatement.setString(1, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(String isbn){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.DELETE_BOOK);
            preparedStatement.setString(1, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book, String oldIsbn){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.UPDATE_BOOK);
            preparedStatement.setString(1, book.getIsbn());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getCategory());
            preparedStatement.setString(4, book.getYear());
            preparedStatement.setString(5, book.getSellingPrice());
            preparedStatement.setString(6, book.getAvailQuantity());
            preparedStatement.setString(7, book.getThreshold());
            preparedStatement.setString(8, book.getPublisherName());
            preparedStatement.setString(9, oldIsbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBookISBN(String text, String isbn) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.UPDATE_BOOK_ISBN);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBookTitle(String text, String isbn) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.UPDATE_BOOK_TITLE);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBookYear(String text, String isbn) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.UPDATE_BOOK_YEAR);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBookCategory(String text, String isbn) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.UPDATE_BOOK_CATEGORY);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBookPrice(String text, String isbn) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.UPDATE_BOOK_PRICE);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBookAvail(String text, String isbn) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.UPDATE_BOOK_AVAILABLE);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBookThreshold(String text, String isbn) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.UPDATE_BOOK_THRESHOLD);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBookPublisherName(String text, String isbn) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.UPDATE_BOOK_PUBLISHER);
            preparedStatement.setString(1, text);
            preparedStatement.setString(2, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean promoteUser(String username){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.PROMOTE_USER);
            preparedStatement.setString(1, username);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String insertOrder(String username, String orderTime){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, orderTime);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            String orderId = "";
            if(resultSet.next()){
                System.out.println(resultSet.getString(1));
                orderId = resultSet.getString(1);

            }
            return orderId;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean insertOrderDetail(String orderID, String bookIsbn, String quantity){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.INSERT_ORDER_DETAIL);
            preparedStatement.setString(1, orderID);
            preparedStatement.setString(2, bookIsbn);
            preparedStatement.setString(3, quantity);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public ResultSet getJoinedOrders(){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.GET_JOINED_ORDERS);
            System.out.println(preparedStatement.toString());
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean deleteOrder(String id, String username){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.DELETE_ORDER);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, username);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteOrderDetail(String id, String isbn){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.DELETE_ORDER_DETAIL);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, isbn);
            System.out.println(preparedStatement.toString());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateCustomer(Customer customer) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.UPDATE_CUSTOMER);
            preparedStatement.setString(1, customer.getUserName());
            preparedStatement.setString(2, customer.getHashedPassword());
            preparedStatement.setString(3, customer.getFirstName());
            preparedStatement.setString(4, customer.getLastName());
            preparedStatement.setString(5, customer.getPhoneNumber());
            preparedStatement.setString(6, customer.getAddress());
            preparedStatement.setString(7, customer.getCredential());
            preparedStatement.setString(8, customer.getEmail());
            preparedStatement.setString(9, customer.getUserName());
            int cnt = preparedStatement.executeUpdate();
            if (cnt == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean addToAvailQuantity(String isbn, String value){
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.INCREASE_BOOK_AVAILABLE);
            preparedStatement.setString(1, value);
            preparedStatement.setString(2, isbn);
            int cnt = preparedStatement.executeUpdate();
            if (cnt == 1) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public double getLastMonthTotalSales() {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.LAST_MONTH_TOTAL_SALES);
            resultSet = preparedStatement.executeQuery();
            double totalSales = 0;
            while (resultSet.next()) {
                totalSales = resultSet.getDouble(SqlCommands.SALES);
            }
            return totalSales;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public ResultSet getTopSellingBooks(String date) {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.TOP_SELLING_BOOKS);
            preparedStatement.setString(1, date);
            System.out.println(preparedStatement.toString());
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Pair<String,Integer>> getTopFiveCustomers() {
        try {
            preparedStatement = connect.prepareStatement(SqlCommands.TOP_FIVE_CUSTOMERS);
            resultSet = preparedStatement.executeQuery();
            ArrayList<Pair<String, Integer>> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(new Pair<>(resultSet.getString(DBContract.Purchase.USER_NAME_COLUMN), resultSet.getInt(SqlCommands.TOTAL_BOOKS)));
            }
            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
