package com.example.orderservice.orderservice;

import com.example.orderservice.orderservice.dto.OrderRequest;
import com.example.orderservice.orderservice.dto.OrderResponse;
import com.example.orderservice.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.orderservice.model.Order;
import com.example.orderservice.orderservice.repository.OrderRepository;
import com.example.orderservice.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class OrderServiceTest {
    @Mock
    OrderRepository orderRepository;
    @InjectMocks
    OrderService orderService;
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreateOrder() {
        OrderRequest orderRequest = createOrderRequest(1,"iphone", new BigDecimal(100.0),10.0);
        when(orderRepository.save(any(Order.class))).thenAnswer(i-> i.getArgument(0));
        String order = orderService.createOrder(orderRequest);
        assertEquals("Order inserted successfully", order);

    }
    @Test
    void testCreateOrderFailure(){
        OrderRequest orderRequest = createOrderRequest(1,"iphone", new BigDecimal(100.0),10.0);
        when(orderRepository.save(any(Order.class))).thenReturn(null);
        String order = orderService.createOrder(orderRequest);
        assertEquals("Order not inserted successfully", order);
    }

    @Test
    void testCreateOrderWhenException(){
        OrderRequest orderRequest = createOrderRequest(1,"iphone", new BigDecimal(100.0),10.0);
        when(orderRepository.save(any(Order.class))).thenThrow(new RuntimeException("Socket timeout"));
        String order = orderService.createOrder(orderRequest);
        assertEquals("Order not inserted successfully", order);
    }
    @Test
    void testGetAllOrders() {
        List<Order> orderList = new ArrayList<>();
        Order order = new Order(1,"iphone", new BigDecimal("10.00"),10.00);
        orderList.add(order);
        when(orderRepository.findAll()).thenReturn(orderList);

        List<OrderResponse> allOrders = orderService.getAllOrders();
        assertTrue(allOrders.size()>0);
    }
    @Test
    void testGetAllOrdersWithException() {
        when(orderRepository.findAll()).thenThrow(new RuntimeException(" Db connection failure"));
        assertThrows(RuntimeException.class, () -> orderService.getAllOrders());
    }

    @Test
    void testGetAllOrdersWithEmpty() {
        when(orderRepository.findAll()).thenReturn(new ArrayList<>());
        List<OrderResponse> allOrders = orderService.getAllOrders();
        assertEquals(0, allOrders.size());
    }
    @Test
    void testGetOrdersById() {
        Order order = new Order(1,"iphone", new BigDecimal("10.00"),10.00);
        when(orderRepository.findById(any(Integer.class))).thenReturn(Optional.of(order));
        OrderResponse ordersById = orderService.getOrdersById(1);
        assertNotNull(ordersById);
        assertEquals(1,ordersById.getOrderId());
    }
    @Test
    void testGetOrdersByIdWhenOrderNotFound() {
        when(orderRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        OrderResponse ordersById = orderService.getOrdersById(1);
        //assertTrue(ordersById==null);
        assertNull(ordersById);
    }
    @Test
    void testGetOrderByName() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(new Order(1,"iphone",new BigDecimal(100.0),10.00));
        orderList.add(new Order(2,"iphone",new BigDecimal(200.0),20.00));
        when(orderRepository.findByOrderName(anyString())).thenReturn(orderList);
        List<OrderResponse> orderResponses = orderService.getOrdersByName("iphone");
        assertEquals(2,orderResponses.size());
        List<OrderResponse> responseList = orderResponses.stream()
                .filter(o -> o.getOrderName().equalsIgnoreCase("iphone")).collect(Collectors.toList());
        assertTrue(responseList.size()>0);
    }
    @Test
    void testUpdateOrder() {
        Order order = createOrder(1,"iphone", new BigDecimal(100.0),10.0);
        when(orderRepository.findById(any(Integer.class))).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenAnswer(i->i.getArgument(0));
        OrderRequest orderRequest = createOrderRequest(1, "iphone", new BigDecimal(100.0), 7.0);
        OrderResponse orderResponse = orderService.updateOrder(orderRequest);
        assertNotNull(orderResponse);
        assertEquals(7.0, orderResponse.getDiscount());
    }
    @Test
    void testUpdateOrderWhenNotFound() {
        when(orderRepository.findById(any(Integer.class))).thenReturn(Optional.empty());
        OrderRequest orderRequest = createOrderRequest(1, "iphone", new BigDecimal(100.0), 7.0);
        OrderResponse orderResponse = orderService.updateOrder(orderRequest);
        assertNull(orderResponse);
    }
    @Test
    void testDeleteOrder(){
        Integer orderId = 1;
        orderService.deleteOrder(orderId);
        verify(orderRepository, atLeast(1)).deleteById(any(Integer.class));
    }

    private Order createOrder(int orderId, String orderName, BigDecimal amount, double discount) {
        Order order = new Order(orderId,orderName,amount,discount);
        return order;
    }

    private OrderRequest  createOrderRequest(Integer orderId, String orderName, BigDecimal amount, Double discount ) {
        OrderRequest orderRequest = new OrderRequest(orderId,orderName,amount,discount);
        return orderRequest;
    }

}



