package org.example.util;

import org.example.companies.Customer;
import org.example.store.StoreOffer;

import java.util.TreeMap;


public class Realization {
    StoreOffer offer;
    OrderProcessing process;
    TreeMap<String, Customer> orders;
    
    public Realization(StoreOffer offer, OrderProcessing orders) {
        this.process = orders;
        this.offer = offer;
        this.orders = this.process.createOrder();
    }
    
    public void getSale() {
        for (Customer value : orders.values()) {
            getAmount(value);
            writerOrder(value);
            offer.discountReduction(5);
        }
    }
    
    private double getAmount (Customer value) {
       return offer.getPrice() * value.getOrderQuantity();
    }
    
    private void writerOrder (Customer value) {
        process.writerOrder(value.getOrderDate() + "|" + value.getName() + "|" + getAmount(value));
    }
}
