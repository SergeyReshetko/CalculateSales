package org.example.adapter;

import org.example.model.Order;

import java.util.List;

public interface OrderAdapter {
    List<Order> orderParse(List<String> textOrders);
}
