package com.edu.shopservice.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Arrays;
import java.util.UUID;

@Getter
@Entity
@ToString(exclude = {"image"})
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "image", schema = "s21")
public class Image {

    @Id
    @Column(name = "id")
    private UUID id;

    private byte[] image;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Image image1 = (Image) o;
        return Arrays.equals(image, image1.image);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(image);
    }
}
