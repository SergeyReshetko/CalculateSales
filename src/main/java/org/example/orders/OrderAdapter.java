package org.example.orders;

import java.util.*;

public class OrderAdapter {
    private final List<String> orders;
    private final List<Order> ordersCompanies = new ArrayList<>();
    
    public OrderAdapter(List<String> orders) {
        this.orders = orders;
    }
    
    public List<Order> getOrdersCompanies() {
        addOrders();
        sortDateOrders();
        return ordersCompanies;
    }
    
    private void sortDateOrders() {
        ordersCompanies.sort(Comparator.comparing(Order::getOrderDate));
    }
    
    private void addOrders() {
        for (String[] temp : separateListOrders()) {
            Order customer = new Order(temp[0], temp[1], temp[2]);
            setOrdersCompanies(customer.getOrder());
        }
    }
    
    private List<String[]> separateListOrders() {
        List<String[]> tempOrders = new ArrayList<>();
        for (String temp : orders) {
            tempOrders.add(temp.split("\\|"));
        }
        return tempOrders;
    }
    
    private void setOrdersCompanies(Order order) {
        ordersCompanies.add(order);
    }
}
