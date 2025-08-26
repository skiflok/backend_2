package com.edu.eventservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductPriceUpdateEvent {
    private Long productId;
    private BigDecimal newPrice;
    private String eventTime;
}
