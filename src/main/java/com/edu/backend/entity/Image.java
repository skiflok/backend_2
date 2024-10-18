package com.edu.backend.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
