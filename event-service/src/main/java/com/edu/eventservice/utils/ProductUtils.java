package com.edu.eventservice.utils;

import com.edu.eventservice.dto.ProductDto;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class ProductUtils {

    public static ProductDto randomProduct(List<ProductDto> products) {
        if (products == null || products.isEmpty()) {
            throw new IllegalArgumentException("Product list is empty or null");
        }
        int index = ThreadLocalRandom.current().nextInt(products.size());
        return products.get(index);
    }
}
