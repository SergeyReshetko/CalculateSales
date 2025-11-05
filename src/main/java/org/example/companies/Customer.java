package org.example.companies;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Customer {
    private final String name;
    private final int orderQuantity;
    private final LocalDateTime orderDate;
    private final String uuid;
    
    public Customer(String date, String name, String quantity) {
        Objects.requireNonNull(date, "date must not be null");
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(quantity, "quantity must not be null");
        this.name = name;
        this.orderQuantity = Integer.parseInt(quantity);
        this.orderDate = LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.uuid = date;
    }
    
    public String getUuid() {
        return uuid;
    }
    
    public String getName() {
        return name;
    }
    
    public int getOrderQuantity() {
        return orderQuantity;
    }
    
    public LocalDateTime getOrderDate() {
        return orderDate;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return orderQuantity == customer.orderQuantity &&
                       Objects.equals(name, customer.name) &&
                       Objects.equals(orderDate, customer.orderDate) &&
                       Objects.equals(uuid, customer.uuid);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name, orderQuantity, orderDate, uuid);
    }
    
    @Override
    public String toString() {
        return "\n name = '" + name + '\'' +
                       ", order = " + orderQuantity +
                       ", date = " + orderDate + '\'';
    }
}
