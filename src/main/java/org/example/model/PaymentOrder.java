package org.example.model;

public class PaymentOrder {
    private final String name;
    private final Double finalPrice;
    
    public PaymentOrder(String name, Double finalPrice) {
        this.name = name;
        this.finalPrice = finalPrice;
    }
    
    public String getName() {
        return name;
    }
    
    public Double getFinalPrice() {
        return finalPrice;
    }
}
