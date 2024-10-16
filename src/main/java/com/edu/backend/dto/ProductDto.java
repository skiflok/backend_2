package com.edu.backend.dto;

import com.edu.backend.entity.Image;
import com.edu.backend.entity.Supplier;
import com.edu.backend.enums.Category;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
public class ProductDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
    @Size(min = 1, max = 100)
    private Category category;

    @NotNull
    @DecimalMin("0")
    private BigDecimal price;

    @Min(0)
    private Integer availableStock;

    private LocalDate lastUpdateDate;

    @NotNull
    private Supplier supplier;

    @NotNull
    private Image image;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProductDto that = (ProductDto) o;
        return Objects.equals(name, that.name) &&
                category == that.category &&
                Objects.equals(price, that.price) &&
                Objects.equals(availableStock, that.availableStock) &&
                Objects.equals(lastUpdateDate, that.lastUpdateDate) &&
                Objects.equals(supplier, that.supplier) &&
                Objects.equals(image, that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, price, availableStock, lastUpdateDate, supplier, image);
    }
}
