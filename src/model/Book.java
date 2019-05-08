package model;

import java.util.ArrayList;

public class Book {
    private String isbn;
    private String title;
    private String sellingPrice;
    private String category;
    private String year;
    private String availQuantity;
    private String defaultOrder;
    private String threshold;


    public void setIsbn(final String isbn) {
        this.isbn = isbn;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public void setSellingPrice(final String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getIsbn() {

        return isbn;
    }

    public String getTitle() {
        return title;
    }
    public String getSellingPrice() {
        return sellingPrice;
    }

    public Book(ArrayList<String> selectedRow) {
        this.isbn = selectedRow.get(0);
        this.title = selectedRow.get(1);
        this.sellingPrice = selectedRow.get(4);
    }

    public Book(String isbn, String title, String category, String year, String sellingPrice, String availQuantity, String threshold, String defaultOrder) {
        this.isbn = isbn;
        this.title = title;
        this.sellingPrice = sellingPrice;
        this.category = category;
        this.year = year;
        this.availQuantity = availQuantity;
        this.defaultOrder = defaultOrder;
        this.threshold = threshold;
    }

    public String getCategory() {
        return category;
    }

    public String getYear() {
        return year;
    }

    public String getAvailQuantity() {
        return availQuantity;
    }

    public String getDefaultOrder() {
        return defaultOrder;
    }

    public String getThreshold() {
        return threshold;
    }

    @Override
    public String toString() {
        return "Book{" +
                "isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", sellingPrice='" + sellingPrice + '\'' +
                ", category='" + category + '\'' +
                ", year='" + year + '\'' +
                ", availQuantity='" + availQuantity + '\'' +
                ", defaultOrder='" + defaultOrder + '\'' +
                ", threshold='" + threshold + '\'' +
                '}';
    }
}
