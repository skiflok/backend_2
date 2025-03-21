package com.edu.authservise.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Builder
@Getter
@Setter
@Entity
@Table(
        name = "users",
        schema = "s21"
)
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id")
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
