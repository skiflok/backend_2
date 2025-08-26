package com.edu.eventservice.utils;

import com.edu.eventservice.dto.ProductDto;
import com.edu.eventservice.dto.event.ProductPriceUpdateEvent;
import com.edu.eventservice.dto.event.ProductStockUpdateEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ProductEventMapper {

    public static ProductDto randomProduct(List<ProductDto> products) {
        return products.get(ThreadLocalRandom.current().nextInt(products.size()));
    }

    public static ProductStockUpdateEvent generateStockEvent(ProductDto product) {
        return ProductStockUpdateEvent.builder()
                .productId(product.getId())
                .decreaseStock(ThreadLocalRandom.current().nextInt(1, 5))
                .eventTime(LocalDateTime.now().toString())
                .build();
    }

    public static ProductPriceUpdateEvent generatePriceEvent(ProductDto product) {
        BigDecimal newPrice = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(10.0, 20.0))
                .setScale(2, RoundingMode.HALF_UP);

        return ProductPriceUpdateEvent.builder()
                .productId(product.getId())
                .newPrice(newPrice)
                .eventTime(LocalDateTime.now().toString())
                .build();
    }
}
