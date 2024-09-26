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
    @SequenceGenerator(name = "imageIdSeq", sequenceName = "image_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imageIdSeq")
    private UUID id;

    @Lob
    private byte[] image;
}
