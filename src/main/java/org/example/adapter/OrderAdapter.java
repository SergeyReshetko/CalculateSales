package org.example.adapter;

import org.example.model.Order;

import java.util.*;

public class OrderAdapter {
    
    public List<Order> formOrdersTextFile(List<String> textOrders) {
        List<Order> orders = new ArrayList<>();
        for (String temp : textOrders) {
            String[] parseOrders = temp.split("\\|");
            Order order = new Order(parseOrders[0], parseOrders[1], Integer.parseInt(parseOrders[2]));
            orders.add(order);
        }
        orders.sort(Comparator.comparing(Order::getOrderDate));
        return orders;
    }
}
