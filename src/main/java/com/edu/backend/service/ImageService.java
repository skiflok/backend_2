package com.edu.backend.service;

import com.edu.backend.entity.Image;
import com.edu.backend.entity.Product;
import com.edu.backend.repository.ImageRepository;
import com.edu.backend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    public byte[] getImageById(UUID uuid) {
        return imageRepository.findById(uuid)
                .map(Image::getImage)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Изображение с [id=%s] не найден", uuid))
                );
    }

    public byte[] getImageByProductId(Long id) {
        return productRepository.findById(id)
                .map(Product::getImage)
                .map(Image::getImage)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Изображение с [id=%d] товара не найдено", id))
                );
    }

    public void deleteById(UUID id) {
        //todo нужна обработка
        imageRepository.deleteById(id);
    }

    public void changeImage(UUID id, MultipartFile image) throws IOException {
        imageRepository.save(Image.builder()
                .id(id)
                .image(image.getBytes())
                .build());
    }

    public void addImageByProductId(Long productId, MultipartFile inputImage) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Продукт с [id=%s] не найден", productId))
                );
        UUID imageUuid = UUID.randomUUID();
        Image image = Image.builder()
                .id(imageUuid)
                .image(inputImage.getBytes())
                .build();
        imageRepository.save(image);
        product.setImage(image);
        productRepository.save(product);
        log.info("Image update success");
    }
}
