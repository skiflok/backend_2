package com.edu.backend.service;

import com.edu.backend.dto.ImageDto;
import com.edu.backend.entity.Image;
import com.edu.backend.entity.Product;
import com.edu.backend.repository.ImageRepository;
import com.edu.backend.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

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
                        new EntityNotFoundException(String.format("Клиент с [id=%s] не найден", uuid))
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
}
