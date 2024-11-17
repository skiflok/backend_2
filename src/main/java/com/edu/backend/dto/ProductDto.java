package com.edu.backend.dto;

import com.edu.backend.enums.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@ToString
@Schema(description = "Товар")
public class ProductDto {

    private Long id;

    @NotNull
    @Size(min = 1, max = 100)
    private String name;

    @NotNull
//    @Size(min = 1, max = 100)
    private Category category;

    @NotNull
    @DecimalMin("0")
    private BigDecimal price;

    @Min(0)
    private Integer availableStock;

    private LocalDate lastUpdateDate;

    @NotNull
    private Long supplierId;

    @NotNull
    private UUID imageUuid;

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
                Objects.equals(imageUuid, that.imageUuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, price, availableStock, lastUpdateDate, supplierId, imageUuid);
    }
}
