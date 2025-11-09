package org.example.util;

import org.example.exception.IORuntimeException;
import org.example.exception.IOTextFileException;

import java.io.*;
import java.util.*;

public class FileUtil {
    
    public List<String> readFile(String path) throws Exception {
        List<String> listOrders = new ArrayList<>();
        FileReader reader = new FileReader(path);
        BufferedReader bufferedReader = new BufferedReader(reader);
        String order;
        
        if (!reader.ready()) {
            throw new IORuntimeException("Ошибка при чтение файла заказов");
        }
        while ((order = bufferedReader.readLine()) != null) {
            listOrders.add(order);
        }
        return listOrders;
    }
    
    public void writeFile(String path, String paymentOrder) throws Exception {
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

