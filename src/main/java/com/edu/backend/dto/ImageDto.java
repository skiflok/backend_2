package com.edu.backend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@Schema(description = "Изображение")
public class ImageDto {

    private UUID id;

    @NotNull(message = "Изображение не должно быть null")
    @Size(min = 1, message = "Изображение не может быть пустым")
    private byte[] image;
}
