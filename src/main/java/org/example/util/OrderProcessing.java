package org.example.util;

import org.example.companies.Customer;
import org.example.companies.CustomerStorage;

import java.util.TreeMap;

public class OrderProcessing extends FileOperation {
    CustomerStorage listOrders;
    
    public TreeMap<String, Customer> createOrder() {
        listOrders = new CustomerStorage(readFile());
        return listOrders.getOrderCompanies();
    }
    
    public void writerOrder(String order) {
        writerFile(order);
    }
}
