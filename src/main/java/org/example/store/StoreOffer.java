package org.example.store;

public class StoreOffer extends PercentageDiscount {
    private final double price;
    private int discount;
    
    public StoreOffer(double price, int discount) {
        this.price = price;
        this.discount = discount;
    }
    
    public double getPrice() {
        return this.price * percentage(this.discount);
    }
    
    public void discountReduction(int percent) {
        this.discount = this.discount - percent;
        percentage(this.discount);
    }
}
