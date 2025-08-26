package com.edu.eventservice.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
