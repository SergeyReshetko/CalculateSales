package org.example.util;

import org.example.exception.IORuntimeException;
import org.example.exception.IOTextFileException;

import java.io.*;
import java.util.*;

public class FileUtil {
    
    public List<String> readFile(String path) throws Exception {
        List<String> listOrders = new ArrayList<>();
        FileReader reader = new FileReader(path);
        String order;
        if (!reader.ready()) {
            try (BufferedReader bufferedReader = new BufferedReader(reader)) {
                while ((order = bufferedReader.readLine()) != null) {
                    listOrders.add(order);
                }
            } catch (IOException e) {
                throw new IOTextFileException("Ошибка при чтение файла заказов");
            }
            return listOrders;
        } else {
            throw new IORuntimeException("Ошибка файл для чтения не готов");
        }
        
    }
    
    public void writerTextFile(String path, String paymentOrder) throws Exception {
        File file = new File(path);
        if (paymentOrder != null || file.createNewFile()) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getName(), true))) {
                bufferedWriter.write(Objects.requireNonNull(paymentOrder));
                bufferedWriter.newLine();
            } catch (IOException e) {
                throw new IOTextFileException("Ошибка при записи файла заказов");
            }
        } else {
            throw new IORuntimeException("Ошибка платежные поручения не сформированы");
        }
    }
}

