package org.example.service;

import org.example.model.Order;
import org.example.model.PaymentOrder;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OrderService {
    
    public List<PaymentOrder> calculateOrders(List<Order> orders, int price, int discount, int reductionNumber) {
        List<PaymentOrder> paymentOrders = new ArrayList<>();
        int finishDiscount = discount;
        for (Order order : orders) {
            PaymentOrder paymentOrder = new PaymentOrder(order.getName(), calculatePrice(price, finishDiscount) * order.getOrderWeight());
            paymentOrders.add(paymentOrder);
            finishDiscount = discountReduction(finishDiscount, reductionNumber);
        }
        return paymentOrders;
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
