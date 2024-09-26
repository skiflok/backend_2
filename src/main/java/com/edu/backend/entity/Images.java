package com.edu.backend.entity;


import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "images", schema = "s21")
public class Images {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "imagesIdSeq", sequenceName = "images_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "imagesIdSeq")
    private UUID id;

    @Lob
    private byte[] image;
}
