package org.example.storage;

import org.example.model.Order;
import org.example.util.FileUtil;

import java.util.List;

public class StorageOrders {
    
    public void writerOrder(FileUtil fileUtil, String pathFileWriter, List<Order> orders) throws Exception {
        for (Order order : orders) {
            fileUtil.writerFile(pathFileWriter, order.getName() + " | " + order.getAmount());
        }
    }
}
