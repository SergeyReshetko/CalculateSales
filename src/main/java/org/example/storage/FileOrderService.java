package org.example.storage;

import org.example.model.PaymentOrder;
import org.example.util.FileUtil;

import java.util.List;

public class FileOrderService {
    
    public void createTextListOrders(FileUtil fileUtil, String pathFileWriter, List<PaymentOrder> paymentOrders) throws Exception {
        for (PaymentOrder order : paymentOrders) {
            fileUtil.writerTextFile(pathFileWriter, order.getName() + " | " + order.getFinalPrice());
        }
    }
}

