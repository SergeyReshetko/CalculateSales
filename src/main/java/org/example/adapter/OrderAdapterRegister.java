package org.example.adapter;


import java.io.File;

public class OrderAdapterRegister {
    
    public static OrderAdapter getOrderAdapter(File path) {
        
        if (!path.getName().contains(".")) {
            return new NoFormatOrderAdapter();
        }
        return new TxtOrderAdapter();
    }
}
