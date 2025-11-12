package org.example;

import org.example.process.OrderProcessing;

public class Main {
    public static void main(String[] args) {
        String pathStemNameFile    = "./discount_day";
        String pathTextFile = "./discount_day.txt";
        String pathToSettlementReceipt = "./Financial_report.txt";
        OrderProcessing sale = new OrderProcessing();
        sale.process(pathTextFile, pathToSettlementReceipt, 10, 50, 5);
        sale.process(pathStemNameFile, pathToSettlementReceipt, 20, 50, 5);
    }
}
