package org.example;

import org.example.process.OrderProcessing;

public class Main {
    public static void main(String[] args) {
        String pathFileOrder = "./discount_day.txt";
        String pathToSettlementReceipt = "./Financial_report.txt";
        
        OrderProcessing sale = new OrderProcessing(pathFileOrder, pathToSettlementReceipt);
        sale.getSale();
        sale.writerOrder();
    }
}
