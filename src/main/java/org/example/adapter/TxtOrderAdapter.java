package org.example.adapter;

import org.example.model.Order;

import java.util.*;

public class TxtOrderAdapter implements OrderAdapter {
    
    public List<Order> orderFormation(List<String> textOrders) {
        List<Order> orders = new ArrayList<>();
        for (String temp : textOrders) {
            String[] splitOrders = orderParse(temp);
            Order order = new Order(splitOrders[0], splitOrders[1], Integer.parseInt(splitOrders[2]));
            orders.add(order);
        }
        orders.sort(Comparator.comparing(Order::getOrderDate));
        return orders;
    }
    
    @Override
    public String[] orderParse(String order) {
        if (order.contains("|")) {
            return order.split("\\|");
        } else if (order.contains("#")) {
            return order.split("#");
        }
        return null;
    }
}
