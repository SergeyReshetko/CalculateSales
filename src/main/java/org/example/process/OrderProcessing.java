package org.example.process;

import org.example.adapter.OrderAdapter;
import org.example.exception.OrderProcessingException;
import org.example.model.PaymentOrder;
import org.example.service.OrderService;
import org.example.model.Order;
import org.example.storage.WriterFile;
import org.example.util.FileUtil;

import java.util.List;

public class OrderProcessing {
    
    public void process(String pathFileRead, String pathFileWriter, int price, int discount, int reductionNumber) {
        try {
            FileUtil fileUtil = new FileUtil();
            OrderAdapter orderAdapter = new OrderAdapter();
            OrderService orderService = new OrderService();
            WriterFile writerFile = new WriterFile();
            List<Order> orders = orderAdapter.formOrdersTextFile(fileUtil.readFile(pathFileRead));
            List<PaymentOrder> paymentOrders = orderService.calculateOrders(orders, price, discount, reductionNumber);
            writerFile.createTextListOrders(fileUtil, pathFileWriter, paymentOrders);
        } catch (Exception e) {
            e.fillInStackTrace();
            throw new OrderProcessingException("Процесс подсчета не завершен ");
        }
    }
}
