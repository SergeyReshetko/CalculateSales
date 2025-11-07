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
    
    public void writerFile(String path, String report) throws Exception {
        File file = new File(path);
        
        FileWriter writer = new FileWriter(file.getName(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);
        
        if (report != null || file.createNewFile()) {
            bufferedWriter.write(Objects.requireNonNull(report));
            bufferedWriter.newLine();
            bufferedWriter.close();
        } else {
            throw new IORuntimeException("Ошибка при записи файла заказов");
        }
    }
}

