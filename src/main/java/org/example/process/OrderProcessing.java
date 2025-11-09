package org.example.process;

import org.example.exception.OrderProcessingException;
import org.example.model.PaymentOrder;
import org.example.service.OrderService;
import org.example.model.Order;
import org.example.storage.FileOrder;
import org.example.util.FileUtil;

import java.util.List;


public class OrderProcessing {
    
    public void process(String pathFileRead, String pathFileWriter, int price, int discount, int reductionNumber) {
        FileUtil fileUtil = new FileUtil();
        try {
            FormOrder formOrder = new FormOrder();
            OrderService orderService = new OrderService();
            List<Order> orders = formOrder.formOrdersTextFile(fileUtil.readFile(pathFileRead));
            List<PaymentOrder> paymentOrders = orderService.calculateOrders(orders, price, discount, reductionNumber);
            FileOrder storageOrders = new FileOrder();
            storageOrders.writerOrder(fileUtil, pathFileWriter, paymentOrders);
        } catch (Exception e) {
            throw new OrderProcessingException("Процесс подсчета не завершен " + e);
        }
    }
}
