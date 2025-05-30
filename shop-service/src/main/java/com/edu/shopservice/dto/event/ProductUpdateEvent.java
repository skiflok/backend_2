package com.edu.shopservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductUpdateEvent {
    private Long productId;
    private BigDecimal newPrice;
    private Integer newStock;
    private String eventTime;
}

