package com.example.orderservice.orderservice.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class OrderRequest {
    @Id
    private Integer orderId;
    @NotBlank(message = "Order name is required")
    private String orderName;
    @Min(value = 0, message = "Amount must be positive")
    private BigDecimal amount;
    @Min(value = 0, message = "Discount must be positive")
    private Double discount;
}
