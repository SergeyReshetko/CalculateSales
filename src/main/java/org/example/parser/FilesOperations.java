package org.example.parser;

public class FilesOperations {
    
    protected String[] parseTextFile(String order) {
        return order.split("\\|");
    }

    protected String[] parseStemNameFile(String order) {
        return order.split("#");
    }
}
