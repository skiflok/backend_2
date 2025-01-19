package com.edu.authservise.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "users"
//        schema = "s21"
)
public class Users {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @SequenceGenerator(name = "clientsIdSeq", sequenceName = "s21.client_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientsIdSeq")
    private Long id;

    @Column(name = "user_name")
    private String name;
}
