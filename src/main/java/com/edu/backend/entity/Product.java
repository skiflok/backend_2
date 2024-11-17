package com.edu.backend.entity;

import com.edu.backend.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@Table(name = "product", schema = "s21")
public class Product {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "productIdSeq", sequenceName = "s21.product_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productIdSeq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class)
    private Category category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "available_stock ")
    private Integer availableStock;

    @Column(name = "last_update_date ")
    private LocalDate lastUpdateDate;

    @ManyToOne
    @JoinColumn(name = "supplier_id ")
    private Supplier supplier;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id ")
    private Image image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) &&
                category == product.category &&
                Objects.equals(price, product.price) &&
                Objects.equals(availableStock, product.availableStock) &&
                Objects.equals(lastUpdateDate, product.lastUpdateDate) &&
                Objects.equals(supplier, product.supplier) &&
                Objects.equals(image, product.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, category, price, availableStock, lastUpdateDate, supplier, image);
    }
}
