package org.example;

import org.example.companies.Customer;
import org.example.companies.CustomerStorage;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.TreeMap;

public class CustomerStorageTest extends CustomerStorage {
    private final TreeMap<String, Customer> test = new TreeMap<>();
    
    public CustomerStorageTest() {
        super(new ArrayList<>());
        Customer customer = new Customer("2021-02-10T08:53:25", "Test name", "1200");
        addOrderCompanies(customer.getUuid(), customer);
        test.put(customer.getUuid(), customer);
    }
    
    @Test
    void testGetOrderCompanies() {
        assertEquals(test, getOrderCompanies());
    }
}
