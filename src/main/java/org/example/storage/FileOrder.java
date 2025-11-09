package org.example.storage;

import org.example.model.PaymentOrder;
import org.example.util.FileUtil;

import java.util.List;

public class FileOrder {
    
    public void writerOrder(FileUtil fileUtil, String pathFileWriter, List<PaymentOrder> paymentOrders) throws Exception {
        for (PaymentOrder order : paymentOrders) {
            fileUtil.writerFile(pathFileWriter, order.getName() + " | " + order.getFinalPrice());
        }
    }
}
