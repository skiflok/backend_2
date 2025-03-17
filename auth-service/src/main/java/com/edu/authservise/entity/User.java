package com.edu.authservise.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
@Entity
@Table(
        name = "users",
        schema = "s21"
)
public class User {
    @Id
    @Column(name = "id")
//    @SequenceGenerator(name = "usersIdSeq", schema = "s21", sequenceName = "users_id_seq", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersIdSeq")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String password;
}
