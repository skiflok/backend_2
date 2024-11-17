package com.edu.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
@Table(name = "supplier", schema = "s21")
public class Supplier {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "supplierIdSeq", sequenceName = "s21.supplier_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "supplierIdSeq")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "address_id ")
    private Address address;

    @Column(name = "phone_number ")
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Supplier supplier = (Supplier) o;
        return Objects.equals(name, supplier.name) &&
                Objects.equals(address, supplier.address) &&
                Objects.equals(phoneNumber, supplier.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phoneNumber);
    }
}