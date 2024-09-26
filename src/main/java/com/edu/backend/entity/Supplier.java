package com.edu.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "supplier", schema = "s21")
public class Supplier {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "supplierIdSeq", sequenceName = "supplier_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplierIdSeq")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "address_id ")
    private Address address;

    @Column(name = "phone_number ")
    private String phoneNumber;
}