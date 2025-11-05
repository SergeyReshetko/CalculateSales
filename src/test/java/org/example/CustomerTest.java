package org.example;

import org.example.companies.Customer;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest extends Customer {
    private final String date = "2021-02-10T08:53:25";
    
    public CustomerTest() {
        super("2021-02-10T08:53:25", "Test name", "1200");
    }
    
    @Test
    void testGetDate() {
        assertEquals(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")), getOrderDate());
    }
    
    @Test
    void testGetName() {
        String name = "Test name";
        assertEquals(name, getName());
    }
    
    @Test
    void testGetQuantity() {
        String quantity = "1200";
        assertEquals(Integer.parseInt(quantity), getOrderQuantity());
    }
    
    @Test
    void testGetUuid() {
        assertEquals(date, getUuid());
    }
}
