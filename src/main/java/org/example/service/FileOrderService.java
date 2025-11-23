package org.example.service;

import io.vavr.control.Try;
import org.example.model.PaymentOrder;
import org.example.util.FileUtil;

import java.util.List;

public class FileOrderService {
    
    public void savePaymentOrders(FileUtil fileUtil, String pathFileWriter, List<PaymentOrder> paymentOrders) {
        paymentOrders.stream()
                .map(order -> Try.run(() -> fileUtil.writeFile(pathFileWriter, order.getName() + " | " + order.getFinalPrice())))
                .forEach(Try::get);
    }
}

