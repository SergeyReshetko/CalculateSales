

package org.example.adapter;

import org.example.model.Order;

import java.util.*;

public class NoFormatOrderAdapter implements OrderAdapter {
    
    @Override
    public List<Order> orderParse(List<String> textOrders) {
        List<Order> orders = new ArrayList<>();
        for (String temp : textOrders) {
            String[] splitOrders = temp.split("#");
            Order order = new Order(splitOrders[0], splitOrders[1], Integer.parseInt(splitOrders[2]));
            orders.add(order);
        }
        orders.sort(Comparator.comparing(Order::getOrderDate));
        return orders;
    }
}