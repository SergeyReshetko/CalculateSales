package org.example.util;

import org.example.exception.IORuntimeException;
import org.example.exception.IOTextFileException;

import java.io.*;
import java.util.*;

public class FileUtil {
    
    public List<String> readFile(File path) throws Exception {
        List<String> listOrders = new ArrayList<>();
        try (FileReader reader = new FileReader(path);
             BufferedReader bufferedReader = new BufferedReader(reader)) {
            
            if (!reader.ready()) {
                throw new IORuntimeException("Ошибка при чтение файла заказов");
            }
            
            String order;
            while ((order = bufferedReader.readLine()) != null) {
                listOrders.add(order);
            }
        } catch (FileNotFoundException e) {
            throw new IORuntimeException("Файл не найден: " + path.getAbsolutePath());
        }
        
        return listOrders;
    }
    
    public void writeFile(String path, String paymentOrder) throws Exception {
        if (paymentOrder == null) {
            throw new IORuntimeException("Ошибка платежные поручения не сформированы");
        }
        
        File file = new File(path);
        
        if (!file.createNewFile()) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getName(), true))) {
                bufferedWriter.write(Objects.requireNonNull(paymentOrder));
                bufferedWriter.newLine();
            } catch (IOException e) {
                throw new IOTextFileException("Ошибка при записи файла заказов");
            }
        }
    }
}
