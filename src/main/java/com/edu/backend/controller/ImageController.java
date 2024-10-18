package com.edu.backend.controller;

import com.edu.backend.dto.ImageDto;
import com.edu.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService service;


//    добавление изображения (на вход подается byte array изображения и id товара).
    public void addImage() {

    }
//    Изменение изображения (на вход подается id изображения и новая строка для замены)
    public void changeImage() {

    }
//    Удаление изображения по id изображения
    public void deleteImage() {

    }
//    Получение изображения конкретного товара (по id товара)
    public ImageDto getImageByProductId() {
        return null;
    }
//    Получение изображения по id изображения
    @GetMapping(
            value = "/{id}",
            produces = "application/octet-stream"
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImageById(@PathVariable UUID id) {
        return service.getImageById(id);
    }



}
