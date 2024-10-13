package com.edu.backend.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "image", schema = "s21")
public class Image {

    @Id
    @Column(name = "id")
    private UUID id;

    private byte[] image;
}
