package org.example;

import org.example.store.PercentageDiscount;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PercetageDiscountTest extends PercentageDiscount {
    @Test
    void percentageTest() {
        assertEquals(0.5, percentage(50));
    }
}
