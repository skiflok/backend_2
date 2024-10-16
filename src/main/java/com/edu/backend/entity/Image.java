package com.edu.backend.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "image", schema = "s21")
@Schema(description = "Изображение")
public class Image {

    @Id
    @Column(name = "id")
    private UUID id;

    private byte[] image;
}
