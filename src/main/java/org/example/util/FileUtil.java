package org.example.util;

import org.example.exception.IORuntimeException;

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
        } else {
            while ((order = bufferedReader.readLine()) != null) {
                listOrders.add(order);
            }
            return listOrders;
        }
        
    }
    
    public void writerTextFile(String path, String report) throws Exception {
        File file = new File(path);
        if (report != null || file.createNewFile()) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file.getName(), true))) {
                bufferedWriter.write(Objects.requireNonNull(report));
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.fillInStackTrace();
            }
        } else {
            throw new IORuntimeException("Ошибка при записи файла заказов");
        }
    }
}

