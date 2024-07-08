package com.example.orderservice.orderservice.repository;

import com.example.orderservice.orderservice.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findByOrderName(String orderName);

    List<Order> findByAmount(BigDecimal amount);

    List<Order> findByDiscount(Double discount);
}
