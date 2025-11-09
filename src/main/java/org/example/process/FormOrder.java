package org.example.process;

import org.example.model.Order;
import org.example.adapter.OrderAdapter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class FormOrder {
    
    public List<Order> formOrdersTextFile(List<String> listOrders) {
        OrderAdapter orderAdapter = new OrderAdapter();
        List<String[]> orders = orderAdapter.separateListOrders(listOrders);
        List<Order> ordersCompanies = new ArrayList<>();
        for (String[] temp : orders) {
            Order order = new Order(temp[0], temp[1], Integer.parseInt(temp[2]));
            ordersCompanies.add(order);
        }
        ordersCompanies.sort(Comparator.comparing(Order::getOrderDate));
        return ordersCompanies;
    }
}
