package org.example.exception;

import java.io.IOException;

public class IOTextFileException extends IOException {
    public IOTextFileException(String message) {
        super(message);
    }
}
