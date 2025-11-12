package org.example.adapter;

import org.example.parser.FilesOperations;
import org.example.parser.OrdersOperationsInterface;

public class OrderAdapter extends FilesOperations implements OrdersOperationsInterface {
    
    @Override
    public String[] orderParse(String order) {
        if (order.contains("|")) {
            return parseTextFile(order);
        } else if (order.contains("#")) {
            return parseStemNameFile(order);
        }
        return null;
    }
}
