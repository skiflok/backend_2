package com.edu.eventservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateEvent {
    private Long productId;
    private BigDecimal newPrice;
    private Integer newStock;
    private String eventTime;
}

