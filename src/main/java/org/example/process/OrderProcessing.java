package org.example.process;

import org.example.adapter.TxtOrderAdapter;
import org.example.exception.OrderProcessingException;
import org.example.model.PaymentOrder;
import org.example.service.OrderService;
import org.example.model.Order;
import org.example.service.FileOrderService;
import org.example.util.FileUtil;

import java.util.List;

public class OrderProcessing {
    private final FileUtil fileUtil;
    private final FileOrderService fileOrderService;
    
    public OrderProcessing(FileUtil fileUtil, FileOrderService fileOrderService) {
        this.fileUtil = fileUtil;
        this.fileOrderService = fileOrderService;
    }
    
    public void process(String pathFileRead, String pathFileWriter, int price, int discount, int reductionNumber) {
        try {
            TxtOrderAdapter txtOrderAdapter = new TxtOrderAdapter();
            OrderService orderService = new OrderService();
            
            List<Order> orders = txtOrderAdapter.orderFormation(fileUtil.readFile(pathFileRead));
            List<PaymentOrder> paymentOrders = orderService.calculateOrders(orders, price, discount, reductionNumber);
            
            fileOrderService.savePaymentOrders(fileUtil, pathFileWriter, paymentOrders);
        } catch (Exception e) {
            throw new OrderProcessingException("Процесс подсчета не завершен ");
        }
    }
}
