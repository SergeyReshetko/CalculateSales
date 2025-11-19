package org.example;

import org.example.process.OrderProcessing;
import org.example.service.FileOrderService;
import org.example.service.OrderService;
import org.example.util.FileUtil;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        String pathNoFormatFile = "./discount_day";
        String pathTextFile = "./discount_day.txt";
        String pathToSettlementReceipt = "./Financial_report.txt";
        
        FileUtil fileUtil = new FileUtil();
        FileOrderService fileOrderService = new FileOrderService();
        OrderService orderService = new OrderService();
        OrderProcessing sale = new OrderProcessing(fileUtil, fileOrderService, orderService);
        
        sale.process(new File(pathTextFile), pathToSettlementReceipt, 10, 50, 5);
        sale.process(new File(pathNoFormatFile), pathToSettlementReceipt, 20, 50, 5);
    }
}
