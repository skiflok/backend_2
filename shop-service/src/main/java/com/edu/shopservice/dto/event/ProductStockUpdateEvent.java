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
public class ProductStockUpdateEvent {
    private Long productId;
    private Integer newStock;
    private Integer decreaseStock;
    private String eventTime;
}
