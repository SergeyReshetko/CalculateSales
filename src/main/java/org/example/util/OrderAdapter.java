package org.example.util;

import java.util.*;

public class OrderAdapter {
    
    public List<String[]> separateListOrders(List<String> orders) {
        List<String[]> parseOrders = new ArrayList<>();
        for (String temp : orders) {
            parseOrders.add(temp.split("\\|"));
        }
        return parseOrders;
    }
}
