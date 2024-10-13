package com.edu.backend.entity;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "address", schema = "s21")
public class Address {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "addressIdSeq", sequenceName = "s21.address_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressIdSeq")
    private Long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;
}
