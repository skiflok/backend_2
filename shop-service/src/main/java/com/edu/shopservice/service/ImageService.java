package com.edu.shopservice.service;

import com.edu.shopservice.entity.Image;
import com.edu.shopservice.entity.Product;
import com.edu.shopservice.repository.ImageRepository;
import com.edu.shopservice.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final RedisTemplate<String, byte[]> redis;
    // TTL кеша 1 час
    private static final Duration TTL = Duration.ofHours(1);

    private String productKey(Long productId) {
        return "product:image:" + productId + ":original";
    }
    private String imageKey(UUID imageId) {
        return "image:" + imageId;
    }

    public byte[] getImageById(UUID uuid) {
        String key = imageKey(uuid);
        byte[] cached = redis.opsForValue().get(key);
        if (cached != null) {
            log.info("get image by redis [uuid = {}]", uuid);
            return cached;
        }
        byte[] fresh = imageRepository.findById(uuid)
                .map(Image::getImage)
                .orElseThrow(() ->
                        new EntityNotFoundException("Image not found: " + uuid));
        log.info("get image by postgres [uuid = {}]", uuid);
        if (fresh.length <= 1_000_000) {
            redis.opsForValue().set(key, fresh, TTL);
            log.info("set image to redis [uuid = {}]", uuid);
        }
        return fresh;
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
        if (!imageRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Изображение с [id=%s] товара не найдено", id));
        }
        imageRepository.deleteById(id);
    }

    public void changeImage(UUID id, MultipartFile image) throws IOException {
        imageRepository.save(Image.builder()
                .id(id)
                .image(image.getBytes())
                .build());
        log.info("Image change success");
    }

    @Transactional
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
