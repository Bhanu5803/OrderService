package com.example.orderservice.orderservice.controller;

import com.example.orderservice.orderservice.dto.OrderRequest;
import com.example.orderservice.orderservice.dto.OrderResponse;
import com.example.orderservice.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class OrderController {
    @Autowired
    OrderService orderService;

    @PostMapping("/create")
    public String createOrder(@RequestBody OrderRequest orderRequest){
       return orderService.createOrder(orderRequest);

    }
    @GetMapping("/getAll")
    public List<OrderResponse> getAllOrders(){
        return orderService.getAllOrders();

    }
@GetMapping("/byId")
    public OrderResponse getOrdersById(Integer id)  {
        return orderService.getOrdersById(id);

    }
@GetMapping("/byName")
    public List<OrderResponse> getOrdersByName(String orderName){
        return orderService.getOrdersByName(orderName);

    }
    @PostMapping("/update")
    public OrderResponse updateOrderByName(@RequestBody OrderRequest orderRequest){
        return orderService.updateOrder(orderRequest);
    }
    @DeleteMapping("/delete")
    public String deleteOrder(Integer orderId){
        return orderService.deleteOrder(orderId);

    }


}
