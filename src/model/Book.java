package model;

import java.util.ArrayList;

public class Book {
    private String isbn;
    private String title;
    private String sellingPrice;


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
}
