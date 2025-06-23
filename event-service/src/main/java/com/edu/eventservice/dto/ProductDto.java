package com.edu.eventservice.dto;

import com.edu.eventservice.enums.Category;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;
    private String name;
    private Category category;
    private BigDecimal price;
    private Integer availableStock;
    private LocalDate lastUpdateDate;
    private Long supplierId;
    private UUID imageId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDto that = (ProductDto) o;
        return Objects.equals(name, that.name) &&
                category == that.category &&
                Objects.equals(price, that.price) &&
                Objects.equals(availableStock, that.availableStock) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(supplierId, that.supplierId) &&
                Objects.equals(imageId, that.imageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, price, availableStock, lastUpdateDate, supplierId, imageId);
    }
}
