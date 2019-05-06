package model;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    HashMap<Book, Integer> orders = new HashMap<>();

    public void setOrders(final HashMap<Book, Integer> orders) {
        this.orders = orders;
    }

    public HashMap<Book, Integer> getOrders() {

        return orders;
    }
}
