package org.example;

import org.example.process.OrderProcessing;
import org.example.service.FileOrderService;
import org.example.util.FileUtil;

public class Main {
    public static void main(String[] args) {
        String pathStemNameFile = "./discount_day";
        String pathTextFile = "./discount_day.txt";
        String pathToSettlementReceipt = "./Financial_report.txt";
        
        FileUtil fileUtil = new FileUtil();
        FileOrderService fileOrderService = new FileOrderService();
        OrderProcessing sale = new OrderProcessing(fileUtil, fileOrderService);
        
        sale.process(pathTextFile, pathToSettlementReceipt, 10, 50, 5);
        sale.process(pathStemNameFile, pathToSettlementReceipt, 20, 50, 5);
    }
}
