package org.example.util;

import org.example.exception.IORuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.api.BeforeEach;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {
    
    private FileUtil fileUtil;
    
    @TempDir
    Path tempDir;
    
    @BeforeEach
    void setUp() {
        fileUtil = new FileUtil();
    }
    
    @Test
    @DisplayName("Успешное чтение файла с несколькими строками")
    void readFile_SuccessfulReading_MultipleLines() throws Exception {
        File testFile = tempDir.resolve("test.txt").toFile();
        List<String> expectedLines = new ArrayList<>(Arrays.asList("Line 1", "Line 2", "Line 3"));
        Files.write(testFile.toPath(), expectedLines);
        
        List<String> actualLines = fileUtil.readFile(testFile);
        
        assertEquals(expectedLines, actualLines);
        assertEquals(3, actualLines.size());
        assertEquals("Line 1", actualLines.get(0));
    }
    
    @Test
    @DisplayName("Чтение несуществующего файла должно бросать исключение")
    void readFile_NonExistentFile_ThrowsException() {
        File nonExistentFile = new File(tempDir.resolve("non_existent_file.txt").toString());
        
        IORuntimeException exception = assertThrows(IORuntimeException.class,
                () -> fileUtil.readFile(nonExistentFile));
        assertTrue(exception.getMessage().contains("Файл не найден"));
    }
    
    @Test
    @DisplayName("FileReader не готов к чтению - бросает IORuntimeException")
    void readFile_FileReaderNotReady_ThrowsIORuntimeException() throws Exception {
        
        File testFile = tempDir.resolve("test.txt").toFile();
        Files.write(testFile.toPath(), Collections.singletonList("test"));
        
        FileUtil fileUtil = new FileUtil();
        
        List<String> result = fileUtil.readFile(testFile);
        assertEquals(1, result.size());
        assertEquals("test", result.get(0));
    }
    
    @Test
    @DisplayName("Ошибка IOException при чтении - бросает Exception")
    void readFile_IOExceptionDuringReading_ThrowsIORuntimeException() {
        File corruptedFile = new File("corrupted://invalid.path"); // Неправильный протокол
        
        IORuntimeException exception = assertThrows(IORuntimeException.class,
                () -> fileUtil.readFile(corruptedFile));
        
        assertNotNull(exception);
    }
    
    @Test
    @DisplayName("Успешная запись в файл")
    void writeFile_SuccessfulWriting_WritesToFile() throws Exception {
        String filePath = "./output.txt";
        String paymentOrder = "Test payment order";
        
        Path path = Paths.get(filePath);
        Files.writeString(path, "");
        fileUtil.writeFile(filePath, paymentOrder);
        
        File outputFile = new File(filePath);
        assertTrue(outputFile.exists());
        
        List<String> lines = Files.readAllLines(outputFile.toPath());
        assertEquals(1, lines.size());
        assertEquals(paymentOrder, lines.get(0));
    }
    
    @Test
    @DisplayName("Дописать в существующий файл")
    void writeFile_AppendToExistingFile_AddsNewLine() throws Exception {
        String filePath = "./output.txt";
        Files.write(new File(filePath).toPath(), Collections.singletonList("Existing line"));
        
        String paymentOrder = "New payment order";
        
        fileUtil.writeFile(filePath, paymentOrder);
        
        List<String> lines = Files.readAllLines(new File(filePath).toPath());
        assertEquals(2, lines.size());
        assertEquals("Existing line", lines.get(0));
        assertEquals("New payment order", lines.get(1));
    }
    
    @Test
    @DisplayName("Запись null значения - бросает IORuntimeException")
    void writeFile_NullPaymentOrder_ThrowsIORuntimeException() {
        String filePath = "./output.txt";
        
        IORuntimeException exception = assertThrows(IORuntimeException.class,
                () -> fileUtil.writeFile(filePath, null));
        
        assertEquals("Ошибка платежные поручения не сформированы", exception.getMessage());
    }
    
    @Test
    @DisplayName("Ошибка при создании файла - бросает исключение")
    void writeFile_CannotCreateFile_ThrowsException() {
        String invalidPath = "/invalid/path/that/does/not/exist/file.txt";
        String paymentOrder = "Test order";
        
        assertThrows(Exception.class,
                () -> fileUtil.writeFile(invalidPath, paymentOrder));
    }
    
    @Test
    @DisplayName("Ошибка при записи в файл - бросает IOTextFileException")
    void writeFile_IOExceptionDuringWriting_ThrowsIOTextFileException() {
        String invalidPath = tempDir.resolve("nonexistent_dir").resolve("file.txt").toString();
        String paymentOrder = "Test order";
        
        assertThrows(IOException.class,
                () -> fileUtil.writeFile(invalidPath, paymentOrder));
    }
    
    @Test
    @DisplayName("Создание нового файла при записи")
    void writeFile_CreatesNewFile_WhenFileDoesNotExist() throws Exception {
        String newFilePath = tempDir.resolve("new file.txt").toString();
        File newFile = new File(newFilePath);
        String paymentOrder = "Test order";
        
        assertFalse(newFile.exists());
        
        fileUtil.writeFile(newFilePath, paymentOrder);
        
        assertTrue(newFile.exists());
    }
    
    @Test
    @DisplayName("Запись пустой строки")
    void writeFile_EmptyString_WritesSuccessfully() throws Exception {
        String filePath = "output.txt";
        String emptyPaymentOrder = "";
        
        Path path = Paths.get(filePath);
        Files.writeString(path, "");
        fileUtil.writeFile(filePath, emptyPaymentOrder);
        
        File outputFile = new File(filePath);
        assertTrue(outputFile.exists());
        
        List<String> lines = Files.readAllLines(outputFile.toPath());
        assertEquals(1, lines.size());
        assertEquals("", lines.get(0));
    }
}