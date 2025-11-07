package org.example.service;

import org.example.orders.Order;
import org.example.orders.OrderAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    private static final String MAX_PERCENT = "100";
    private final List<String> orderReport = new ArrayList<>();
    private int discount = 50;
    OrderAdapter listOrder;
    
    public List<String> getOrderReport() {
        return orderReport;
    }
    
    public OrderService(List<String> listOrders) {
        this.listOrder = new OrderAdapter(listOrders);
    }
    
    public void calculateOrders() {
        for (Order order : listOrder.getOrdersCompanies()) {
            orderReport.add(order.getName() + " - " + getFullPrice(order.getOrderWeight()));
            discountReduction();
        }
        orderReport.forEach(System.out::println);
    }
    
    private double getFullPrice(int orderWeight) {
        return calculatePrice() * orderWeight;
    }
    
    private double calculatePrice() {
        int price = 10;
        return price * percentage(this.discount);
    }
    
    private void discountReduction() {
        int reductionNumber = 5;
        this.discount -= reductionNumber;
    }
    
    private double percentage(int percentage) {
        BigDecimal converter = new BigDecimal(String.valueOf(percentage));
        BigDecimal result = converter.divide(new BigDecimal(MAX_PERCENT), 2, RoundingMode.HALF_EVEN);
        return 1 - result.doubleValue();
    }
}
