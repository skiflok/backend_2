package com.edu.backend.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "address", schema = "s21")
public class Address {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "addressIdSeq", sequenceName = "address_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "addressIdSeq")
    private UUID id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "street")
    private String street;
}
