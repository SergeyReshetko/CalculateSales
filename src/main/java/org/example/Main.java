package org.example;

import org.example.store.StoreOffer;
import org.example.util.OrderProcessing;
import org.example.util.Realization;

public class Main {
    public static void main(String[] args) {
        StoreOffer offer = new StoreOffer(10, 50);
        OrderProcessing orders = new OrderProcessing();
        Realization sale = new Realization(offer, orders);
        sale.getSale();
    }
}
