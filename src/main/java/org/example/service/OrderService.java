package org.example.service;

import org.example.model.Order;
import org.example.util.OrderAdapter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class OrderService {
    
    public List<Order> calculateOrders(List<String> listOrders, int price, int discount, int reductionNumber) {
        OrderAdapter orderAdapter = new OrderAdapter();
        List<Order> orders = formOrders(orderAdapter.separateListOrders(listOrders));
        int finishDiscount = discount;
        for (Order order : orders) {
            order.setAmount(calculatePrice(price, finishDiscount) * order.getOrderWeight());
            finishDiscount = discountReduction(finishDiscount, reductionNumber);
        }
        return orders;
    }
    
    private List<Order> formOrders(List<String[]> orders) {
        List<Order> ordersCompanies = new ArrayList<>();
        for (String[] temp : orders) {
            Order order = new Order(temp[0], temp[1], Integer.parseInt(temp[2]));
            ordersCompanies.add(order);
        }
        ordersCompanies.sort(Comparator.comparing(Order::getOrderDate));
        return ordersCompanies;
    }
    
    private double calculatePrice(int price, int discount) {
        return price * percentage(discount);
    }
    
    private int discountReduction(int discount, int reductionNumber) {
        if (discount != 0) {
            discount -= reductionNumber;
        }
        return discount;
    }
    
    private double percentage(int percentage) {
        String maxPercent = "100";
        BigDecimal converter = new BigDecimal(String.valueOf(percentage));
        BigDecimal result = converter.divide(new BigDecimal(maxPercent), 2, RoundingMode.HALF_EVEN);
        return 1 - result.doubleValue();
    }
}
