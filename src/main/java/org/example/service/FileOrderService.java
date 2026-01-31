package org.example.service;

import org.example.model.PaymentOrder;
import org.example.util.FileUtil;

import java.util.List;

public class FileOrderService {
    
    public void savePaymentOrders(FileUtil fileUtil, String pathFileWriter, List<PaymentOrder> paymentOrders) {
        paymentOrders.stream()
                .map(paymentOrder -> paymentOrder.getFinalPrice() + " | " + paymentOrder.getFinalPrice())
                .forEach(s -> fileUtil.writeFile(pathFileWriter, s));
    }
}

