package org.example.model;

import java.util.Objects;

public class Order {
    private final String name;
    private final int orderWeight;
    private final String orderDate;
    
    public Order(String date, String name, int orderWeight) {
        Objects.requireNonNull(date, "date must not be null");
        Objects.requireNonNull(name, "name must not be null");
        this.name = name;
        this.orderWeight = orderWeight;
        this.orderDate = date;
    }
    
    public String getName() {
        return name;
    }
    
    public int getOrderWeight() {
        return orderWeight;
    }
    
    public String getOrderDate() {
        return orderDate;
    }
}
