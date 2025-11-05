package org.example.companies;

import java.util.ArrayList;
import java.util.TreeMap;

public class CustomerStorage {
    private final TreeMap<String, Customer> orderCompanies = new TreeMap<>();
    
    public CustomerStorage(ArrayList<String> orders) {
        for (String[] temp : setConvertListOrders(orders)) {
            Customer customer = new Customer(temp[0], temp[1], temp[2]);
            addOrderCompanies(customer.getUuid(), customer);
        }
    }
    
    private ArrayList<String[]> setConvertListOrders(ArrayList<String> listOrders) {
        ArrayList<String[]> orders = new ArrayList<>();
        for (String temp : listOrders) {
            orders.add(temp.split("\\|"));
        }
        return orders;
    }
    
    public void addOrderCompanies(String uuid, Customer orderCompany) {
        this.orderCompanies.put(uuid, orderCompany);
    }
    
    public TreeMap<String, Customer> getOrderCompanies() {
        return this.orderCompanies;
    }
}
