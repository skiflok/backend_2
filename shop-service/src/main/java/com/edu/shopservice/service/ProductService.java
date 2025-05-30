package com.edu.shopservice.service;

import com.edu.shopservice.dto.ProductDto;
import com.edu.shopservice.dto.event.ProductUpdateEvent;
import com.edu.shopservice.entity.Image;
import com.edu.shopservice.entity.Product;
import com.edu.shopservice.entity.Supplier;
import com.edu.shopservice.repository.ImageRepository;
import com.edu.shopservice.repository.ProductRepository;
import com.edu.shopservice.repository.SupplierRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper defaultModelMapper;
    private final ObjectMapper defaultObjectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.out.product.update-event.topic}")
    private String updateEventTopic;

    public void addProduct(ProductDto productDto) {
        Supplier supplier = supplierRepository.findById(productDto.getSupplierId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("Поставщик с [id=%d] не найден", productDto.getSupplierId()))
        );
        Image image = imageRepository.findById(productDto.getImageId()).orElseThrow(() ->
                new EntityNotFoundException(String.format("изображение с [id=%s] не найдено", productDto.getImageId()))
        );
        Product product = defaultModelMapper.map(productDto, Product.class);
        product.setImage(image);
        product.setSupplier(supplier);
        product.setLastUpdateDate(LocalDate.now());
        productRepository.save(product);
        log.info("addProduct [{}]", product);
    }

    public ProductDto getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> defaultModelMapper.map(product, ProductDto.class))
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format("Продукт с [id=%d] не найден", id))
                );
    }

    public void decreaseProduct(Long productId, Integer decreaseStockValue) {
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new EntityNotFoundException(String.format("Продукт с [id=%d] не найден", productId))
        );
        if (decreaseStockValue > product.getAvailableStock()) {
            throw new IllegalArgumentException("Количество товара меньше чем decreaseStockValue");
        }
        product.setAvailableStock(product.getAvailableStock() - decreaseStockValue);
        productRepository.save(product);
        log.info("product update [{}]", product);
    }

    public List<ProductDto> getAllProduct() {
        return productRepository.findAll().stream()
                .map((element) -> defaultModelMapper.map(element, ProductDto.class))
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("Продукт с [id=%s] не найден", id));
        }
        productRepository.deleteById(id);
    }

    @Transactional
    public void updateStock(Long productId, int newStock) {
        if (newStock < 0) {
            throw new IllegalArgumentException("Cannot update stock. new stock less zero");
        }
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product with id=" + productId));
        p.setAvailableStock(newStock);
        p.setLastUpdateDate(LocalDate.now());
        p = productRepository.save(p);
        log.info("Stock updated for [product_id {}] → {}", productId, newStock);

        ProductUpdateEvent event = ProductUpdateEvent.builder()
                .productId(productId)
                .newStock(p.getAvailableStock())
                .newPrice(p.getPrice())
                .eventTime(p.getLastUpdateDate().toString())
                .build();

        try {
            kafkaTemplate.send(updateEventTopic,
                    productId.toString(),
                    defaultObjectMapper.writeValueAsString(event));
            //                .addCallback(
//                        success -> log.info("Sent update-event to Kafka: {}", eventJson),
//                        failure -> log.error("Failed to send update-event to Kafka: {}", eventJson, failure)
//                );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }

    @Transactional
    public void updatePrice(Long productId, BigDecimal newPrice) {
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cannot update price. new price less zero");
        }
        Product p = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("No product with id=" + productId));
        p.setPrice(newPrice);
        p.setLastUpdateDate(LocalDate.now());
        productRepository.save(p);
        log.info("Price updated for [product_id {}] → {}", productId, newPrice);
    }
}
