package org.example.process;

import org.example.adapter.OrderAdapter;
import org.example.adapter.OrderAdapterRegister;
import org.example.exception.OrderProcessingException;
import org.example.model.Order;
import org.example.model.PaymentOrder;
import org.example.service.FileOrderService;
import org.example.service.OrderService;
import org.example.util.FileUtil;

import java.io.File;
import java.util.List;

public class OrderProcessing {
    private final FileUtil fileUtil;
    private final FileOrderService fileOrderService;
    
    public OrderProcessing(FileUtil fileUtil, FileOrderService fileOrderService) {
        this.fileUtil = fileUtil;
        this.fileOrderService = fileOrderService;
    }
    
    public void process(File pathFileRead, String pathFileWriter, int price, int discount, int reductionNumber) {
        try {
            OrderAdapter orderAdapter = OrderAdapterRegister.getOrderAdapter(pathFileRead);
            OrderService orderService = new OrderService();
            
            List<Order> orders = orderAdapter.orderParse(fileUtil.readFile(pathFileRead));
            List<PaymentOrder> paymentOrders = orderService.calculateOrders(orders, price, discount, reductionNumber);
            
            fileOrderService.savePaymentOrders(fileUtil, pathFileWriter, paymentOrders);
        } catch (Exception e) {
            throw new OrderProcessingException("Процесс подсчета не завершен " + e);
        }
    }
}
