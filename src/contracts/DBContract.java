package contracts;

public class DBContract {
    // will be used to specify the relations and attributes as constants to achieve minimum decoupling
    public static final String DB_USERNAME = "example";
    public static final String DB_PASSWORD = "example";

    public static final String DB_NAME = "book_store";

    public static final String USER_TABLE = "USER";
    public static final String AUTHOR_TABLE = "AUTHOR";
    public static final String BOOK_TABLE = "BOOK";
    public static final String ORDER_TABLE = "ORDER";
    public static final String PUBLISHER_TABLE = "PUBLISHER";
    public static final String PURCHASE_TABLE = "PURCHASE";

    public static class User{
        public static final String USER_NAME_COLUMN = "user_name";
        public static final String PASSWORD_COLUMN = "password";
        public static final String FIRST_NAME_COLUMN = "first_name";
        public static final String LAST_NAME_COLUMN = "last_name";
        public static final String PHONE_COLUMN = "phone";
        public static final String ADDRESS_COLUMN = "address";
        public static final String CREDENTIAL_COLUMN = "credential";
        public static final String EMAIL_COLUMN = "email";
    }
    public static class Author{
        public static final String ISBN_COLUMN = "isbn";
        public static final String AUTHOR_COLUMN = "author";
    }
    public static class Book{
        public static final String ISBN_COLUMN = "isbn";
        public static final String TITLE_COLUMN = "title";
        public static final String CATEGORY_COLUMN = "category";
        public static final String PUBLICATION_YEAR_COLUMN = "publication_year";
        public static final String SELLING_PRICE_COLUMN = "selling_price";
        public static final String AVAILABLE_COLUMN = "available_quantity";
        public static final String THRESHOLD_COLUMN = "threshold";
        public static final String DEFAULT_ORDER_QUANTITY = "default_order";
    }
    public static class Order{
        public static final String ORDER_ID_COLUMN = "order_id";
        public static final String USER_NAME_COLUMN = "user_name";
        public static final String ORDER_TIME_COLUMN = "order_time";
        public static final String QUANTITY_COLUMN = "quantity";
        public static final String BOOK_ISBN_COLUMN = "book_isbn";
    }
    public static class Purchase{
        public static final String PURCHASE_ID_COLUMN = "purchase_id";
        public static final String USER_NAME_COLUMN = "user_name";
        public static final String purchase_TIME_COLUMN = "purchase_time";
        public static final String QUANTITY_COLUMN = "quantity";
        public static final String BOOK_ISBN_COLUMN = "book_isbn";
    }
    public static class Publisher{
        public static final String ISBN_COLUMN = "isbn";
        public static final String NAME_COLUMN = "name";
        public static final String ADDRESS_COLUMN = "address";
        public static final String PHONE_COLUMN = "phone";
    }
}
