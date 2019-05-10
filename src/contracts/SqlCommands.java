package contracts;

public class SqlCommands {
    public static final String SALES = "total_sales";
    public static final String TOTAL_BOOKS = "books";
    public static final String SEARCH_USERNAME = "SELECT " + DBContract.User.USER_NAME_COLUMN +
            " FROM " + DBContract.USER_TABLE +" WHERE " +  DBContract.User.USER_NAME_COLUMN  + " = ?;";
    public static final String GET_PASSWORD = "SELECT " + DBContract.User.PASSWORD_COLUMN +
            " FROM " + DBContract.USER_TABLE +" WHERE " +  DBContract.User.USER_NAME_COLUMN  + " = ?;";
    public static final String INSERT_USER = "INSERT INTO " + DBContract.USER_TABLE + " VALUES(?, ?, ?, ?, ?, ?, ?, ?);" ;
    public static final String GET_USER = "SELECT * FROM " + DBContract.USER_TABLE + " WHERE " + DBContract.User.USER_NAME_COLUMN + " = ?;";
    public static final String Search_For_Book = "select b.isbn, title, category, publication_year, selling_price, available_quantity, threshold, publisher_name from book b where "+
            "( ? = '' OR b.isbn = ?)" +
            " and ( ? = '' OR b.title = ?)" +
            " and ( ? = '' OR b.category = ?)" +
            " and ( ? = '' OR b.publication_year = ?)" +
            " and ( ? = '' OR b.selling_price = ?)" +
            " and ( ? = '' OR b.available_quantity = ?)" +
            " and ( ? = '' OR b.threshold = ?)" +
            " and ( ? = '' OR b.publisher_name = ?)" +
            " and ( ? = '' OR EXISTS (select * from author  where author.isbn = b.isbn and author.author = ?) );";
    public static final String INSERT_BOOK = "INSERT INTO " + DBContract.BOOK_TABLE + " VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String INSERT_AUTHOR = "INSERT INTO " + DBContract.AUTHOR_TABLE + " VALUES(?, ?);";
    public static final String INSERT_PUBLISHER = "INSERT INTO " + DBContract.PUBLISHER_TABLE + " VALUES(?, ?, ?);";
    public static final String GET_BOOK = "SELECT * FROM " + DBContract.BOOK_TABLE + " WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String GET_AUTHORS = "SELECT * FROM " + DBContract.AUTHOR_TABLE + " WHERE " + DBContract.Author.ISBN_COLUMN + " = ?;";
    public static final String GET_PUBLISHER = "SELECT * FROM " + DBContract.PUBLISHER_TABLE + " WHERE " + DBContract.Publisher.NAME_COLUMN + " = ?;";
    public static final String DELETE_AUTHORS = "DELETE FROM " + DBContract.AUTHOR_TABLE + " WHERE " + DBContract.Author.ISBN_COLUMN + " = ?;";
    public static final String DELETE_BOOK = "DELETE FROM " + DBContract.BOOK_TABLE + " WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String UPDATE_BOOK_ISBN = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            DBContract.Book.ISBN_COLUMN + " = ? WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String UPDATE_BOOK_TITLE = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            DBContract.Book.TITLE_COLUMN + " = ? WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String UPDATE_BOOK_CATEGORY = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            DBContract.Book.CATEGORY_COLUMN + " = ? WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String UPDATE_BOOK_YEAR = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            DBContract.Book.PUBLICATION_YEAR_COLUMN + " = ? WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String UPDATE_BOOK_PRICE = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            DBContract.Book.SELLING_PRICE_COLUMN + " = ? WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String UPDATE_BOOK_AVAILABLE = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            DBContract.Book.AVAILABLE_COLUMN + " = ? WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String UPDATE_BOOK_THRESHOLD = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            DBContract.Book.THRESHOLD_COLUMN + " = ? WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String UPDATE_BOOK_PUBLISHER = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            DBContract.Book.PUBLISHER_NAME_COLUMN + " = ? WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";


    public static final String INSERT_PURCHASE = "INSERT INTO " + DBContract.PURCHASE_TABLE +" (user_name, purchase_time, credit_card, credit_card_cvv, credit_card_expiration) VALUES(?, ?, ?, ?, ?);";
    public static final String INSERT_PURCHASE_DETAILS = "INSERT INTO purchase_detail VALUES (?, ?, ?);";
    public static final String LAST_ID = "SELECT LAST_INSERT_ID() from " + DBContract.PURCHASE_TABLE + " ;";


    public static final String PROMOTE_USER = "UPDATE " + DBContract.USER_TABLE + " SET " +
            DBContract.User.CREDENTIAL_COLUMN + " = 'manager' WHERE " + DBContract.User.USER_NAME_COLUMN + " = ?;";
    public static final String UPDATE_BOOK = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            DBContract.Book.ISBN_COLUMN + " = ?, " +
            DBContract.Book.TITLE_COLUMN + " = ?, " +
            DBContract.Book.CATEGORY_COLUMN + " = ?, " +
            DBContract.Book.PUBLICATION_YEAR_COLUMN + " = ?, " +
            DBContract.Book.SELLING_PRICE_COLUMN + " = ?, " +
            DBContract.Book.AVAILABLE_COLUMN + " = ?, " +
            DBContract.Book.THRESHOLD_COLUMN + " = ?, " +
            DBContract.Book.PUBLISHER_NAME_COLUMN + " = ?" +
            " WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String INSERT_ORDER = "INSERT INTO " + DBContract.ORDER_TABLE + "(" + DBContract.Order.USER_NAME_COLUMN + "," + DBContract.Order.ORDER_TIME_COLUMN + ")" + " VALUES(?, ?);";
    public static final String INSERT_ORDER_DETAIL = "INSERT INTO " + DBContract.ORDER_DETAIL_TABLE + " VALUES(?, ?, ?);";;
    public static final String GET_JOINED_ORDERS = "SELECT " +
            " o." + DBContract.Order.ORDER_ID_COLUMN + ", " + DBContract.Order.USER_NAME_COLUMN + ", " +
            DBContract.Order.ORDER_TIME_COLUMN + ", " + DBContract.OrderDetail.BOOK_ISBN_COLUMN + ", " +
            DBContract.OrderDetail.QUANTITY_COLUMN
            + " FROM " + DBContract.ORDER_TABLE
            + " o JOIN " + DBContract.ORDER_DETAIL_TABLE + " d ON o." + DBContract.Order.ORDER_ID_COLUMN + " = d." + DBContract.OrderDetail.ORDER_ID_COLUMN + ";";
    public static final String DELETE_ORDER = "DELETE FROM " + DBContract.ORDER_TABLE + " WHERE " + DBContract.Order.ORDER_ID_COLUMN + " = ? AND " + DBContract.Order.USER_NAME_COLUMN + " = ?;";
    public static final String DELETE_ORDER_DETAIL = "DELETE FROM " + DBContract.ORDER_DETAIL_TABLE + " WHERE " + DBContract.OrderDetail.ORDER_ID_COLUMN + " = ? AND " + DBContract.OrderDetail.BOOK_ISBN_COLUMN + " = ?;";
    public static final String INCREASE_BOOK_AVAILABLE = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            DBContract.Book.AVAILABLE_COLUMN + " = " + DBContract.Book.AVAILABLE_COLUMN + " + ? WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String UPDATE_CUSTOMER = "UPDATE " + DBContract.USER_TABLE + " SET " + DBContract.User.USER_NAME_COLUMN + "=?," +
            DBContract.User.PASSWORD_COLUMN + "=?," +  DBContract.User.FIRST_NAME_COLUMN + "=?," + DBContract.User.LAST_NAME_COLUMN + "=?," +
            DBContract.User.PHONE_COLUMN + "=?," + DBContract.User.ADDRESS_COLUMN + "=?," + DBContract.User.CREDENTIAL_COLUMN + "=?," +
            DBContract.User.EMAIL_COLUMN + "=?" + " WHERE " + DBContract.User.USER_NAME_COLUMN + " = ?;";
    public static final String LAST_MONTH_ALL_SALES = "SELECT (book.selling_price * purchase_detail.quantity) AS sales, purchase.purchase_time "+
            "FROM book " +
            "JOIN purchase_detail ON book.isbn = purchase_detail.book_isbn " +
            "JOIN purchase ON purchase_detail.purchase_id = purchase.purchase_id " +
            "HAVING YEAR(purchase.purchase_time) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "AND MONTH(purchase.purchase_time) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)";
    public static final String LAST_MONTH_TOTAL_SALES = "SELECT SUM(res.sales) as " + SALES + " FROM (" + LAST_MONTH_ALL_SALES + ") res;";
    public static final String TOP_SELLING_BOOKS = "SELECT " + DBContract.PurchaseDetail.BOOK_ISBN_COLUMN + ", SUM(" + DBContract.PurchaseDetail.QUANTITY_COLUMN + ") TotalQuantity"
            + " FROM " + DBContract.PURCHASE_DETAIL_TABLE + " d"
            + " JOIN " + DBContract.PURCHASE_TABLE + " p"
            + " ON d." + DBContract.PurchaseDetail.PURCHASE_ID_COLUMN + " = p." + DBContract.Purchase.PURCHASE_ID_COLUMN
            + " WHERE " + DBContract.Purchase.PURCHASE_TIME_COLUMN + " >= ?"
            + " GROUP BY " + DBContract.PurchaseDetail.BOOK_ISBN_COLUMN
            + " ORDER BY " + "TotalQuantity"
            + " DESC LIMIT 10;";
    public static final String TOP_FIVE_CUSTOMERS = "SELECT purchase.user_name, SUM(purchase_detail.quantity) as " + TOTAL_BOOKS + " ,purchase.purchase_time "+
            "FROM purchase " +
            "JOIN purchase_detail ON purchase.purchase_id = purchase_detail.purchase_id " +
            "GROUP BY purchase.user_name " +
            "HAVING (YEAR(purchase.purchase_time) = YEAR(CURRENT_DATE - INTERVAL 2 MONTH) " +
            "AND MONTH(purchase.purchase_time) = MONTH(CURRENT_DATE - INTERVAL 2 MONTH)) " +
            "OR (YEAR(purchase.purchase_time) = YEAR(CURRENT_DATE - INTERVAL 1 MONTH) " +
            "AND MONTH(purchase.purchase_time) = MONTH(CURRENT_DATE - INTERVAL 1 MONTH)) " +
            "OR (YEAR(purchase.purchase_time) = YEAR(CURRENT_DATE) " +
            "AND MONTH(purchase.purchase_time) = MONTH(CURRENT_DATE)) " +
            "ORDER BY SUM(purchase_detail.quantity) DESC " +
            "LIMIT 5";
}

