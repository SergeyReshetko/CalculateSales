package org.example.store;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class PercentageDiscount {
    private static final String MAX_PERCENT = "100";
    
    protected double percentage(int percentage) {
        BigDecimal converter = new BigDecimal(String.valueOf(percentage));
        BigDecimal result = converter.divide(new BigDecimal(MAX_PERCENT), 2, RoundingMode.HALF_EVEN);
        return 1 - result.doubleValue();
    }
}
