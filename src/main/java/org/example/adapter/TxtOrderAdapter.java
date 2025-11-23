package org.example.adapter;

import org.example.model.Order;

import java.util.*;
import java.util.stream.Collectors;

public class TxtOrderAdapter implements OrderAdapter {
    
    @Override
    public List<Order> orderParse(List<String> textOrders) {
        return textOrders.stream()
                       .map(order -> order.split("\\|"))
                       .map(order -> new Order(order[0], order[1], Integer.parseInt(order[2])))
                       .sorted()
                       .collect(Collectors.toList());
    }
}