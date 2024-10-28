package com.edu.backend.controller;

import com.edu.backend.dto.ImageDto;
import com.edu.backend.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    @PatchMapping(
            value = "/change",
            consumes = "multipart/form-data"
    )
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void changeImage(@RequestPart UUID id,
                            @RequestPart MultipartFile image) throws IOException {
        service.changeImage(id, image);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable UUID id) {
        service.deleteById(id);
    }

    @GetMapping(
            value = "/by-prodyct-id/{id}",
            produces = "application/octet-stream"
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImageByProductId(@PathVariable Long id) {
        return service.getImageByProductId(id);
    }

    @GetMapping(
            value = "/{id}",
            produces = "application/octet-stream"
    )
    @ResponseStatus(HttpStatus.OK)
    public byte[] getImageById(@PathVariable UUID id) {
        return service.getImageById(id);
    }
}
