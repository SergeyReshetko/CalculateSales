package org.example.process;

import org.example.service.OrderService;
import org.example.model.Order;
import org.example.storage.StorageOrders;
import org.example.util.FileUtil;

import java.util.List;


public class OrderProcessing {
    
    public void process(String pathFileRead, String pathFileWriter, int price, int discount, int reductionNumber) {
        FileUtil fileUtil = new FileUtil();
        try {
            OrderService offer = new OrderService();
            List<Order> orders;
            orders = offer.calculateOrders(fileUtil.readFile(pathFileRead), price, discount, reductionNumber);
            StorageOrders storageOrders = new StorageOrders();
            storageOrders.writerOrder(fileUtil, pathFileWriter, orders);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
