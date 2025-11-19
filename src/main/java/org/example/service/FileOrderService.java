package org.example.service;

import org.example.model.PaymentOrder;
import org.example.util.FileUtil;

import java.util.List;

public class FileOrderService {
    
    public void savePaymentOrders(FileUtil fileUtil, String pathFileWriter, List<PaymentOrder> paymentOrders) throws Exception {
        for (PaymentOrder order : paymentOrders) {
            fileUtil.writeFile(pathFileWriter, order.getName() + " | " + order.getFinalPrice());
        }
    }
}

