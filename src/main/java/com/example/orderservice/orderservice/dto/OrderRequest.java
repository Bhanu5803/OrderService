package com.example.orderservice.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderRequest {

        @Id
        private Integer orderId;
    private String orderName;
    private BigDecimal amount;
    private Double discount;
}
