package org.example.process;

import org.example.adapter.OrderAdapter;
import org.example.adapter.OrderAdapterRegister;
import org.example.exception.OrderProcessingException;
import org.example.model.Order;
import org.example.model.PaymentOrder;
import org.example.service.FileOrderService;
import org.example.service.OrderService;
import org.example.util.FileUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/*
Основные тестовые сценарии:
Позитивный сценарий:
Проверяет успешное выполнение всего процесса

Использует MockedStatic для Mocking статического метода OrderAdapterRegister.getOrderAdapter()

Проверяет, что все зависимости вызываются с правильными параметрами

Негативные сценарии:
Ошибка адаптера - когда не удается получить адаптер для файла

Ошибка чтения файла - проблемы с чтением исходного файла

Ошибка parsing - некорректный формат данных в файле

Ошибка расчета - проблемы при вычислении платежей

Ошибка сохранения - сбой при записи результатов

Граничный случай:
Пустой список заказов - проверка обработки файла без данных
 */

//@ExtendWith(MockitoExtension.class) - включает поддержку Mockito в JUnit 5
@ExtendWith(MockitoExtension.class)
class OrderProcessingTest {
    // Mock зависимостей - создаются для всех зависимостей тестируемого класса
    @Mock
    private FileUtil fileUtil;
    
    @Mock
    private FileOrderService fileOrderService;
    
    @Mock
    private OrderService orderService;
    
    // Тестируемый класс
    private OrderProcessing orderProcessing;
    
    // Тестовые данные
    private File testFile;
    private String testOutputPath;
    private int testPrice;
    private int testDiscount;
    private int testReductionNumber;
    
    // @BeforeEach - метод инициализации, который выполняется перед каждым тестом
    @BeforeEach
    void setUp() {
        
        // Создание экземпляра тестируемого класса
        orderProcessing = new OrderProcessing(fileUtil, fileOrderService, orderService);
        
        // Инициализация тестовых данных
        testFile = new File("test_orders_1.txt");
        testOutputPath = "output_orders.txt";
        testPrice = 100;
        testDiscount = 10;
        testReductionNumber = 5;
    }
    
    // @DisplayName позволяет задавать пользовательское отображаемое имя
    @Test
    @DisplayName("Успешная обработка заказов - полный положительный сценарий")
    void process_SuccessfulProcessing_ShouldCompleteWithoutErrors() throws Exception {
        /*
        Используем try-with-resources для MockedStatic
        Использует MockedStatic для Mocking статического метода OrderAdapterRegister.getOrderAdapter()
         */
        try (MockedStatic<OrderAdapterRegister> adapterRegisterMock = mockStatic(OrderAdapterRegister.class)) {
            // Arrange
            OrderAdapter orderAdapter = mock(OrderAdapter.class);
            List<String> fileContent = Arrays.asList("test_orders_2.txt", "test_orders_3.txt");
            List<Order> parsedOrders = Arrays.asList(
                    new Order("2021-02-09T16:00:22", "order1", 2),
                    new Order("2021-02-09T16:00:22", "order2", 3)
            );
            List<PaymentOrder> calculatedOrders = Arrays.asList(
                    new PaymentOrder("order1", 2.2),
                    new PaymentOrder("order2", 3.3)
            );
            
            
            // Настройка - Проверяет, что все зависимости вызываются с правильными параметрами
            /*
            when() — статический метод Mockito, принимает вызов метода на Mock и возвращает экземпляр
            thenReturn() - метод Mockito, который позволяет задать возвращаемое значение для вызова метода мок-объекта.
             */
            when(fileUtil.readFile(testFile)).thenReturn(fileContent);
            adapterRegisterMock.when(() -> OrderAdapterRegister.getOrderAdapter(testFile))
                    .thenReturn(orderAdapter);
            when(orderAdapter.orderParse(fileContent)).thenReturn(parsedOrders);
            when(orderService.calculateOrders(parsedOrders, testPrice, testDiscount, testReductionNumber))
                    .thenReturn(calculatedOrders);
            /*
            Метод doNothing() в Mockito для Java позволяет указать,
            что при вызове метода на мок-объекте ничего не должно происходить
             */
            doNothing().when(fileOrderService)
                    .savePaymentOrders(fileUtil, testOutputPath, calculatedOrders);
            
            // assertDoesNotThrow() метод класса Assertions, который проверяет, что блок кода не вызывает исключение.
            assertDoesNotThrow(() -> orderProcessing.
                                             process(testFile, testOutputPath,
                                                     testPrice, testDiscount,
                                                     testReductionNumber)
            );
            
            /*
             Verify - - это метод Mockito, который позволяет проверить,
             был ли вызван определенный метод мок-объекта с заданными параметрами и сколько раз
             */
            verify(fileUtil).readFile(testFile);
            adapterRegisterMock.verify(() -> OrderAdapterRegister.getOrderAdapter(testFile));
            verify(orderAdapter).orderParse(fileContent);
            verify(orderService).calculateOrders(parsedOrders, testPrice, testDiscount, testReductionNumber);
            verify(fileOrderService).savePaymentOrders(fileUtil, testOutputPath, calculatedOrders);
        }
    }
    
    @Test
    @DisplayName("Ошибка при получении адаптера - должно выбросить OrderProcessingException")
    void process_AdapterNotFound_ShouldThrowOrderProcessingException() throws Exception {
        try (MockedStatic<OrderAdapterRegister> adapterRegisterMock = mockStatic(OrderAdapterRegister.class)) {
            /*
            thenThrow() - метод Mockito, который позволяет настроить
             мок-объект на выбрасывание исключения при вызове определенного метода.
             */
            adapterRegisterMock.when(() -> OrderAdapterRegister.getOrderAdapter(testFile))
                    .thenThrow(new RuntimeException("Adapter not found"));
            
            OrderProcessingException exception =
                    assertThrows(OrderProcessingException.class, () -> orderProcessing.
                                                                               process(
                                                                                       testFile, testOutputPath,
                                                                                       testPrice, testDiscount,
                                                                                       testReductionNumber)
                    );
            
            assertEquals("Процесс подсчета не завершен", exception.getMessage());
            
            /*
            never() - это метод Mockito, который позволяет проверить,
            что определенный метод мок-объекта никогда не вызывался во время выполнения теста.
             */
            adapterRegisterMock.verify(() -> OrderAdapterRegister.getOrderAdapter(testFile));
            verify(fileUtil, never()).readFile(any());
            verify(orderService, never()).calculateOrders(anyList(), anyInt(), anyInt(), anyInt());
            verify(fileOrderService, never()).savePaymentOrders(any(), anyString(), anyList());
        }
    }
    
    @Test
    @DisplayName("Ошибка при чтении файла - должно выбросить OrderProcessingException")
    void process_FileReadError_ShouldThrowOrderProcessingException() throws Exception {
        try (MockedStatic<OrderAdapterRegister> adapterRegisterMock = mockStatic(OrderAdapterRegister.class)) {
            OrderAdapter orderAdapter = mock(OrderAdapter.class);
            
            adapterRegisterMock.when(() -> OrderAdapterRegister.getOrderAdapter(testFile))
                    .thenReturn(orderAdapter);
            when(fileUtil.readFile(testFile)).thenThrow(new RuntimeException("File read error"));
            
            OrderProcessingException exception =
                    assertThrows(OrderProcessingException.class, () -> orderProcessing.
                                                                               process(testFile, testOutputPath,
                                                                                       testPrice, testDiscount,
                                                                                       testReductionNumber)
                    );
            
            assertEquals("Процесс подсчета не завершен", exception.getMessage());
            
            verify(fileUtil).readFile(testFile);
            verify(orderAdapter, never()).orderParse(anyList());
            verify(orderService, never()).calculateOrders(anyList(), anyInt(), anyInt(), anyInt());
        }
    }
    
    @Test
    @DisplayName("Ошибка при parsing заказов - должно выбросить OrderProcessingException")
    void testOrderParsingError() throws Exception {
        try (MockedStatic<OrderAdapterRegister> adapterRegisterMock = mockStatic(OrderAdapterRegister.class)) {
            OrderAdapter orderAdapter = mock(OrderAdapter.class);
            List<String> fileContent = new ArrayList<>(Collections.singletonList("invalid,data"));
            
            adapterRegisterMock.when(() -> OrderAdapterRegister.getOrderAdapter(testFile))
                    .thenReturn(orderAdapter);
            when(fileUtil.readFile(testFile)).thenReturn(fileContent);
            when(orderAdapter.orderParse(fileContent)).thenThrow(new RuntimeException("Parsing error"));
            
            OrderProcessingException exception =
                    assertThrows(OrderProcessingException.class, () -> orderProcessing.
                                                                               process(testFile, testOutputPath,
                                                                                       testPrice, testDiscount,
                                                                                       testReductionNumber)
                    );
            
            assertEquals("Процесс подсчета не завершен", exception.getMessage());
            
            verify(orderAdapter).orderParse(fileContent);
            verify(orderService, never()).calculateOrders(anyList(), anyInt(), anyInt(), anyInt());
        }
    }
    
    @Test
    @DisplayName("Ошибка при расчете заказов - должно выбросить OrderProcessingException")
    void testOrderCalculationError() throws Exception {
        try (MockedStatic<OrderAdapterRegister> adapterRegisterMock = mockStatic(OrderAdapterRegister.class)) {
            OrderAdapter orderAdapter = mock(OrderAdapter.class);
            List<String> fileContent = Collections.singletonList("order1,2");
            List<Order> parsedOrders = Collections.singletonList(new Order(
                    "2021-02-09T16:00:22", "order1", 2));
            
            adapterRegisterMock.when(() -> OrderAdapterRegister.getOrderAdapter(testFile))
                    .thenReturn(orderAdapter);
            when(fileUtil.readFile(testFile)).thenReturn(fileContent);
            when(orderAdapter.orderParse(fileContent)).thenReturn(parsedOrders);
            when(orderService.calculateOrders(parsedOrders, testPrice, testDiscount, testReductionNumber))
                    .thenThrow(new RuntimeException("Calculation error"));
            
            OrderProcessingException exception =
                    assertThrows(OrderProcessingException.class, () -> orderProcessing.
                                                                               process(testFile, testOutputPath,
                                                                                       testPrice, testDiscount,
                                                                                       testReductionNumber)
                    );
            
            assertEquals("Процесс подсчета не завершен", exception.getMessage());
            
            verify(orderService).calculateOrders(parsedOrders, testPrice, testDiscount, testReductionNumber);
            verify(fileOrderService, never()).savePaymentOrders(any(), anyString(), anyList());
        }
    }
    
    @Test
    @DisplayName("Ошибка при сохранении результатов - должно выбросить OrderProcessingException")
    void testSaveResultsError() throws Exception {
        try (MockedStatic<OrderAdapterRegister> adapterRegisterMock = mockStatic(OrderAdapterRegister.class)) {
            OrderAdapter orderAdapter = mock(OrderAdapter.class);
            List<String> fileContent = Collections.singletonList("order1,2");
            List<Order> parsedOrders = Collections.singletonList(new Order(
                    "2021-02-09T16:00:22", "order1", 2));
            List<PaymentOrder> calculatedOrders = Collections.singletonList(
                    new PaymentOrder("order1", 2.3)
            );
            
            adapterRegisterMock.when(() -> OrderAdapterRegister.getOrderAdapter(testFile))
                    .thenReturn(orderAdapter);
            when(fileUtil.readFile(testFile)).thenReturn(fileContent);
            when(orderAdapter.orderParse(fileContent)).thenReturn(parsedOrders);
            when(orderService.calculateOrders(parsedOrders, testPrice, testDiscount, testReductionNumber))
                    .thenReturn(calculatedOrders);
            doThrow(new RuntimeException("Save error")).when(fileOrderService)
                    .savePaymentOrders(fileUtil, testOutputPath, calculatedOrders);
            
            OrderProcessingException exception =
                    assertThrows(OrderProcessingException.class, () -> orderProcessing.
                                                                               process(testFile, testOutputPath,
                                                                                       testPrice, testDiscount,
                                                                                       testReductionNumber)
                    );
            
            assertEquals("Процесс подсчета не завершен", exception.getMessage());
            
            verify(fileOrderService).savePaymentOrders(fileUtil, testOutputPath, calculatedOrders);
        }
    }
    
    @Test
    @DisplayName("Обработка пустого списка заказов - должен завершиться успешно")
    void process_EmptyOrderList_ShouldCompleteSuccessfully() throws Exception {
        try (MockedStatic<OrderAdapterRegister> adapterRegisterMock = mockStatic(OrderAdapterRegister.class)) {
            OrderAdapter orderAdapter = mock(OrderAdapter.class);
            List<String> fileContent = Collections.emptyList();
            List<Order> parsedOrders = Collections.emptyList();
            List<PaymentOrder> calculatedOrders = Collections.emptyList();
            
            adapterRegisterMock.when(() -> OrderAdapterRegister.getOrderAdapter(testFile))
                    .thenReturn(orderAdapter);
            when(fileUtil.readFile(testFile)).thenReturn(fileContent);
            when(orderAdapter.orderParse(fileContent)).thenReturn(parsedOrders);
            when(orderService.calculateOrders(parsedOrders, testPrice, testDiscount, testReductionNumber))
                    .thenReturn(calculatedOrders);
            doNothing().when(fileOrderService)
                    .savePaymentOrders(fileUtil, testOutputPath, calculatedOrders);
            
            assertDoesNotThrow(() -> orderProcessing.
                                             process(testFile, testOutputPath,
                                                     testPrice, testDiscount, testReductionNumber)
            );
            
            verify(orderService).calculateOrders(parsedOrders, testPrice, testDiscount, testReductionNumber);
            verify(fileOrderService).savePaymentOrders(fileUtil, testOutputPath, calculatedOrders);
        }
    }
}