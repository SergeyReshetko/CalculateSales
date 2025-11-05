package org.example;

import org.example.store.StoreOffer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StoreOfferTest extends StoreOffer {
    
    public StoreOfferTest() {
        super(10, 50);
    }
    
    @Test
    void getPriceTest() {
        assertEquals(5, getPrice());
    }
    
    @Test
    void discountReductionTest () {
        discountReduction(5);
        assertEquals(5.5 ,getPrice());
    }
}
