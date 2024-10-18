package com.edu.backend.controller;

import com.edu.backend.dto.ImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequiredArgsConstructor
@RequestMapping("/api/v1/image")
public class ImageController {

    private final ImageService imageService;


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
    public void getImageById() {

    }



}
