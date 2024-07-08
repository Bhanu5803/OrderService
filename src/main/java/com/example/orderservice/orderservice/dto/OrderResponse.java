package com.example.orderservice.orderservice.dto;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderResponse {

        @Id
        private Integer orderId;
    private String orderName;
    private BigDecimal amount;
    private Double discount;
    }
