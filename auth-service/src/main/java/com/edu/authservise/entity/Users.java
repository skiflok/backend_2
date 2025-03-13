package com.edu.authservise.entity;

import jakarta.persistence.*;

@Entity
@Table(
        name = "users",
        schema = "s21"
)
public class Users {
    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "usersIdSeq", schema = "s21", sequenceName = "users_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersIdSeq")
    private Long id;

//    @Column(name = "user_name")
    private String email;
    private String first_name;
    private String last_name;
    private String phone_number;
    private String password;
}
