package org.example.util;

import java.io.*;
import java.util.*;

public abstract class FileOperation {
    private static final String PATH_FILE_ORDER = "./discount_day.txt";
    private static final String PATH_FILE_WRITER = "./List orders.txt";
    private final File file = new File(PATH_FILE_WRITER);
    
    public ArrayList<String> readFile() {
        ArrayList<String> listOrders = new ArrayList<>();
        String order;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PATH_FILE_ORDER));
            while ((order = reader.readLine()) != null) {
                listOrders.add(order);
            }
            return listOrders;
        } catch (IOException e) {
            return listOrders;
        }
    }
    
    public void writerFile(String order) {
        try {
            FileWriter writer = new FileWriter(this.file.getName(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);
            
            if (this.file.createNewFile()) {
                System.out.println("Файл создан");
            }
            
            if (order != null) {
                bufferedWriter.write(order);
                bufferedWriter.newLine();
                bufferedWriter.close();
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи файла Заказов" + e.getMessage());
        }
    }
}

