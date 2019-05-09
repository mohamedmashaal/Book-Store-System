package contracts;

public class SqlCommands {
    public static final String SEARCH_USERNAME = "SELECT " + DBContract.User.USER_NAME_COLUMN +
            " FROM " + DBContract.USER_TABLE +" WHERE " +  DBContract.User.USER_NAME_COLUMN  + " = ?;";
    public static final String GET_PASSWORD = "SELECT " + DBContract.User.PASSWORD_COLUMN +
            " FROM " + DBContract.USER_TABLE +" WHERE " +  DBContract.User.USER_NAME_COLUMN  + " = ?;";
    public static final String INSERT_USER = "INSERT INTO " + DBContract.USER_TABLE + " VALUES(?, ?, ?, ?, ?, ?, ?, ?);" ;
    public static final String GET_USER = "SELECT * FROM " + DBContract.USER_TABLE + " WHERE " + DBContract.User.USER_NAME_COLUMN + " = ?;";
    public static final String Search_For_Book = "select b.isbn, title, category, publication_year, selling_price, available_quantity, threshold, publisher_name from book b left join author a on b.isbn = a.isbn where "+
            "( ? = '' OR b.isbn = ?)" +
            " and ( ? = '' OR b.title = ?)" +
            " and ( ? = '' OR b.category = ?)" +
            " and ( ? = '' OR b.publication_year = ?)" +
            " and ( ? = '' OR b.selling_price = ?)" +
            " and ( ? = '' OR b.available_quantity = ?)" +
            " and ( ? = '' OR b.threshold = ?)" +
            " and ( ? = '' OR b.publisher_name = ?)" +
            " and ( ? = '' OR a.author = ? );";
    public static final String INSERT_BOOK = "INSERT INTO " + DBContract.BOOK_TABLE + " VALUES(?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String INSERT_AUTHOR = "INSERT INTO " + DBContract.AUTHOR_TABLE + " VALUES(?, ?);";
    public static final String INSERT_PUBLISHER = "INSERT INTO " + DBContract.PUBLISHER_TABLE + " VALUES(?, ?, ?);";
    public static final String GET_BOOK = "SELECT * FROM " + DBContract.BOOK_TABLE + " WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
    public static final String GET_AUTHORS = "SELECT * FROM " + DBContract.AUTHOR_TABLE + " WHERE " + DBContract.Author.ISBN_COLUMN + " = ?;";
    public static final String GET_PUBLISHER = "SELECT * FROM " + DBContract.PUBLISHER_TABLE + " WHERE " + DBContract.Publisher.NAME_COLUMN + " = ?;";
    public static final String UPDATE_BOOK = "UPDATE " + DBContract.BOOK_TABLE + " SET " +
            " ? = ? WHERE " + DBContract.Book.ISBN_COLUMN + " = ?;";
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
    public static final String UPDATE_CUSTOMER = "UPDATE " + DBContract.USER_TABLE + " SET " + DBContract.User.USER_NAME_COLUMN + "=?," +
        DBContract.User.PASSWORD_COLUMN + "=?," +  DBContract.User.FIRST_NAME_COLUMN + "=?," + DBContract.User.LAST_NAME_COLUMN + "=?," +
            DBContract.User.PHONE_COLUMN + "=?," + DBContract.User.ADDRESS_COLUMN + "=?," + DBContract.User.CREDENTIAL_COLUMN + "=?," +
            DBContract.User.EMAIL_COLUMN + "=?" + " WHERE " + DBContract.User.USER_NAME_COLUMN + " = ?;";
}

