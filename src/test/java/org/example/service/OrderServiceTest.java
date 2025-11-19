package org.example.service;

import org.example.model.Order;
import org.example.model.PaymentOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    
    @InjectMocks
    private OrderService orderService;
    
    private List<Order> orders;
    private final int BASE_PRICE = 100;
    private final int DISCOUNT = 20;
    private final int REDUCTION_NUMBER = 5;
    
    @BeforeEach
    void setUp() {
        orders = new ArrayList<>();
        orders.add(new Order("2021-02-09T12:32:48", "Order1", 2));
        orders.add(new Order("2021-02-09T12:32:48", "Order2", 3));
        orders.add(new Order("2021-02-09T12:32:48", "Order3", 1));
    }
    
    @Test
    @DisplayName("Should calculate orders with correct prices and discount reduction")
    void calculateOrders_WithValidInput_ShouldReturnCorrectPaymentOrders() {
        List<PaymentOrder> result = orderService.calculateOrders(orders, BASE_PRICE, DISCOUNT, REDUCTION_NUMBER);
        
        assertNotNull(result);
        assertEquals(3, result.size());
        
        PaymentOrder firstOrder = result.get(0);
        assertEquals("Order1", firstOrder.getName());
        assertEquals(160.0, firstOrder.getFinalPrice());
        
        PaymentOrder secondOrder = result.get(1);
        assertEquals("Order2", secondOrder.getName());
        assertEquals(255.0, secondOrder.getFinalPrice());
        
        PaymentOrder thirdOrder = result.get(2);
        assertEquals("Order3", thirdOrder.getName());
        assertEquals(90.0, thirdOrder.getFinalPrice());
    }
    
    @Test
    @DisplayName("Should handle zero discount correctly")
    void calculateOrders_WithZeroDiscount_ShouldApplyNoDiscount() {
        int zeroDiscount = 0;
        
        List<PaymentOrder> result = orderService.calculateOrders(orders, BASE_PRICE, zeroDiscount, REDUCTION_NUMBER);
        
        PaymentOrder firstOrder = result.get(0);
        assertEquals(200.0, firstOrder.getFinalPrice());
        
        PaymentOrder secondOrder = result.get(1);
        assertEquals(300.0, secondOrder.getFinalPrice());
    }
    
    @Test
    @DisplayName("Should handle empty orders list")
    void calculateOrders_WithEmptyList_ShouldReturnEmptyList() {
        List<Order> emptyOrders = new ArrayList<>();
        
        List<PaymentOrder> result = orderService.calculateOrders(emptyOrders, BASE_PRICE, DISCOUNT, REDUCTION_NUMBER);
        
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
    
    @Test
    @DisplayName("Should not reduce discount below zero")
    void calculateOrders_WithDiscountReductionBelowZero_ShouldNotGoBelowZero() {
        List<Order> manyOrders = new ArrayList<>(Arrays.asList(
                new Order("2021-02-09T12:32:48", "Order1", 1),
                new Order("2021-02-09T12:32:48", "Order2", 1),
                new Order("2021-02-09T12:32:48", "Order3", 1),
                new Order("2021-02-09T12:32:48", "Order4", 1),
                new Order("2021-02-09T12:32:48", "Order5", 1),
                new Order("2021-02-09T12:32:48", "Order6", 1)
        ));
        int smallDiscount = 10;
        int largeReduction = 5;
        
        List<PaymentOrder> result = orderService.calculateOrders(manyOrders, BASE_PRICE, smallDiscount, largeReduction);
        
        assertEquals(6, result.size());
        
        PaymentOrder lastOrder = result.get(5);
        assertEquals(100.0, lastOrder.getFinalPrice());
    }
    
    @Test
    @DisplayName("Should handle edge case with negative price")
    void calculateOrders_WithNegativePrice_ShouldCalculateCorrectly() {
        int negativePrice = -50;
        
        List<PaymentOrder> result = orderService.calculateOrders(
                orders, negativePrice, DISCOUNT, REDUCTION_NUMBER);
        
        PaymentOrder firstOrder = result.get(0);
        assertEquals(-80.0, firstOrder.getFinalPrice());
    }
    
    @Test
    @DisplayName("Should handle single order correctly")
    void calculateOrders_WithSingleOrder_ShouldReturnSinglePaymentOrder() {
        List<Order> singleOrder = new ArrayList<>(
                Collections.singletonList(
                        new Order("2021-02-09T12:32:48", "SingleOrder", 5)));
        
        List<PaymentOrder> result = orderService.calculateOrders(singleOrder, BASE_PRICE, DISCOUNT, REDUCTION_NUMBER);
        
        assertEquals(1, result.size());
        PaymentOrder paymentOrder = result.get(0);
        assertEquals("SingleOrder", paymentOrder.getName());
        assertEquals(400.0, paymentOrder.getFinalPrice());
    }
}