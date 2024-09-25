package com.edu.backend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Client {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "clientsIdSeq", sequenceName = "clients_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "clientsIdSeq")
    private Long id;

}
