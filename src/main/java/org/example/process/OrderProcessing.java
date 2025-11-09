package org.example.process;

import org.example.adapter.OrderAdapter;
import org.example.exception.OrderProcessingException;
import org.example.model.PaymentOrder;
import org.example.service.OrderService;
import org.example.model.Order;
import org.example.service.FileOrderService;
import org.example.util.FileUtil;

import java.util.List;

public class OrderProcessing {
    
    public void process(String pathFileRead, String pathFileWriter, int price, int discount, int reductionNumber) {
        try {
            FileUtil fileUtil = new FileUtil();
            OrderAdapter orderAdapter = new OrderAdapter();
            OrderService orderService = new OrderService();
            FileOrderService fileOrderService = new FileOrderService();
            List<Order> orders = orderAdapter.formOrdersTextFile(fileUtil.readFile(pathFileRead));
            List<PaymentOrder> paymentOrders = orderService.calculateOrders(orders, price, discount, reductionNumber);
            fileOrderService.savePaymentOrders(fileUtil, pathFileWriter, paymentOrders);
        } catch (Exception e) {
            throw new OrderProcessingException("Процесс подсчета не завершен ");
        }
    }
}
