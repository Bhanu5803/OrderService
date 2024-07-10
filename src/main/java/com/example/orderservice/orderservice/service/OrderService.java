package com.example.orderservice.orderservice.service;

import com.example.orderservice.orderservice.dto.OrderRequest;
import com.example.orderservice.orderservice.dto.OrderResponse;
import com.example.orderservice.orderservice.exception.OrderNotFoundException;
import com.example.orderservice.orderservice.model.Order;
import com.example.orderservice.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    @Autowired
    OrderRepository orderRepository;


    public String createOrder(OrderRequest orderRequest) {
       Order order = Order.builder()
               .orderName(orderRequest.getOrderName())
               .amount(orderRequest.getAmount())
               .discount(orderRequest.getDiscount()).build();
       Order orderObj = null;
       try {
            orderObj = orderRepository.save(order);
       } catch (Exception e) {
           log.error("Error while calling save method : {}",e.getMessage());
       }

        if (orderObj != null) {
            return "Order inserted successfully";
        }
        return "Order not inserted successfully";
    }


    public List<OrderResponse> getAllOrders() {
        List<Order> orderList = orderRepository.findAll();
        return orderList.stream().map(this::mapOrderResponse).toList();
    }

    private OrderResponse mapOrderResponse(Order orders) {
       return OrderResponse.builder().orderId(orders.getOrderId())
                .orderName(orders.getOrderName())
                .amount(orders.getAmount())
                .discount(orders.getDiscount()).build();
    }

    public OrderResponse getOrdersById(Integer id)  {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isPresent()) {
            return mapOrderResponse(byId.get());
        } else {
            log.error("No orders found with id: " + id);
            return null;
        }
    }

    public List<OrderResponse> getOrdersByName(String orderName) {
        List<Order> byName = orderRepository.findByOrderName(orderName);
       return byName.stream().map(this::mapOrderResponse).toList();
    }

    public OrderResponse updateOrder(OrderRequest orderRequest) {
        Optional<Order> byId = orderRepository.findById(orderRequest.getOrderId());
        if (byId.isPresent()) {
            Order order = byId.get();
            order.setOrderName(orderRequest.getOrderName());
            order.setAmount(orderRequest.getAmount());
            order.setDiscount(orderRequest.getDiscount());
            Order updatedOrder = orderRepository.save(order);
            return OrderResponse.builder()
                    .orderId(updatedOrder.getOrderId())
                    .orderName(updatedOrder.getOrderName())
                    .amount(updatedOrder.getAmount())
                    .discount(updatedOrder.getDiscount())
                    .build();

        } else {
            log.error("Order not updated for given order Id : {}", orderRequest.getOrderId());
            return null;
        }
    }

    public String deleteOrder(Integer orderId) {
        orderRepository.deleteById(orderId);
        return "Order with order Id : " +  orderId  + " deleted successfully ";
    }
}
