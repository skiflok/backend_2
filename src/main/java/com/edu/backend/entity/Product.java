package com.edu.backend.entity;

import com.edu.backend.enums.Category;
import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Entity
@Table(name = "product", schema = "s21")
public class Product {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "productIdSeq", sequenceName = "product_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productIdSeq")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "available_stock ")
    private Integer availableStock;

    @Column(name = "last_update_date ")
    private LocalDate lastUpdateDate;

    // todo FKey
    @Column(name = "supplier_id ")
    private Long supplierId;

    @Column(name = "image_id ")
    private UUID imageId;
}
