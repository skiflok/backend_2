package com.edu.backend.entity;

import com.edu.backend.enums.Gender;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Entity
@Table(name = "client", schema = "s21")
public class Client {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientsIdSeq", sequenceName = "clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientsIdSeq")
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "client_surname")
    private String clientSurname;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING) // Используйте EnumType.ORDINAL, если хотите хранить индекс
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "registration_date")
    private LocalDate registrationDate;

    // todo foreign key
    @Column(name = "address_id")
    private Long addressId;

}
