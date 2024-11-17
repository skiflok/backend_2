package com.edu.backend.controller;

import com.edu.backend.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Tag(name = "ImageController", description = "Контроллер для работы с изображениями")
@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService service;

    @Operation(
            summary = "добавление изображения",
            description = "добавление изображения (на вход подается byte array изображения и id товара)"
    )
    @PostMapping(
            value = "/add",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseStatus(HttpStatus.CREATED)
    public void addImage(@RequestParam Long productId,
                         @RequestPart MultipartFile image) throws IOException {
        service.addImageByProductId(productId, image);
    }

    @Operation(
            summary = "Изменение изображения",
            description = "Изменение изображения (на вход подается id изображения и новая строка для замены"
    )
    @PatchMapping(
            value = "/change",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeImage(@RequestParam UUID id,
                            @RequestPart MultipartFile image) throws IOException {
        service.changeImage(id, image);
    }

    @Operation(
            summary = "Удаление изображения",
            description = "Удаление изображения по id изображения"
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable UUID id) {
        service.deleteById(id);
    }

    @GetMapping(
            value = "/by-product-id/{id}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @Operation(
            summary = "Получение изображения",
            description = "Получение изображения конкретного товара (по id товара)"
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImageByProductId(@PathVariable Long id) {
        return service.getImageByProductId(id);
    }

    @GetMapping(
            value = "/{id}",
            produces = MediaType.APPLICATION_OCTET_STREAM_VALUE
    )
    @Operation(
            summary = "Получение изображения",
            description = "Получение изображения по id изображения"
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImageById(@PathVariable UUID id) {
        return service.getImageById(id);
    }
}
