package org.example.process;

import org.example.service.OrderService;
import org.example.util.FileUtil;


public class OrderProcessing {
    private final String pathFileRead;
    private final String pathFileWriter;
    OrderService offer;
    
    public OrderProcessing(String pathFileRead, String pathFileWriter) {
        this.pathFileRead = pathFileRead;
        this.pathFileWriter = pathFileWriter;
    }
    
    public void getSale() {
        FileUtil readerOrders = new FileUtil();
        try {
            offer = new OrderService(readerOrders.readFile(this.pathFileRead));
            offer.calculateOrders();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void writerOrder() {
        FileUtil writerReport = new FileUtil();
        for (String report : offer.getOrderReport()) {
            try {
                writerReport.writerFile(pathFileWriter, report);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
