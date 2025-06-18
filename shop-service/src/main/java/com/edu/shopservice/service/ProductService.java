package com.edu.shopservice.service;

import com.edu.shopservice.dto.ProductDto;
import com.edu.shopservice.dto.event.ProductPriceUpdateEvent;
import com.edu.shopservice.dto.event.ProductStockUpdateEvent;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    //todo рефакторинг сервиса в части оптимизации запросов

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper defaultModelMapper;
    private final ObjectMapper defaultObjectMapper;
    //todo мб вынести отправку в отдельный кафка сервис?
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.out.product.update-event.topic}")
    private String updateEventTopic;
    //todo need config and topic in kafka cluster
    @Value("${kafka.out.product.update-stock-event.topic}")
    private String updateStockEventTopic;
    @Value("${kafka.out.product.update-price-event.topic}")
    private String updatePriceEventTopic;

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

    @Transactional
    public void decreaseProduct(Long productId, Integer decreaseStockValue) {

        ProductDto productDto = getProductById(productId);
        //todo нужно сохранять время изменения
        int result = productRepository.decreaseStock(productId, decreaseStockValue);
        if (result == 0) {
            log.warn("Недостаточно товара на складе [id={}]", productId);
            //todo need change exception to notify by owner
            throw new IllegalArgumentException("Недостаточно товара на складе");
        }
        //todo тут может неактуальные данные по количеству из за того что в бд уже могло обновиться количество
        ProductStockUpdateEvent event = ProductStockUpdateEvent.builder()
                .decreaseStock(decreaseStockValue)
                .productId(productId)
                .newStock(productDto.getAvailableStock() - decreaseStockValue)
                .eventTime(LocalDateTime.now().toString())
                .build();
        try {
            kafkaTemplate.send(updateStockEventTopic,
                    productId.toString(),
                    defaultObjectMapper.writeValueAsString(event));
        } catch (JsonProcessingException e) {
            log.warn("JsonProcessingException", e);
        }
        log.info("decrease product success [id = {}] [decreaseStockValue = {}]", productId, decreaseStockValue);
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
    public void updatePrice(Long productId, BigDecimal newPrice) throws JsonProcessingException {
        log.info("update price start [product_id = {}] [new price =  {}]", productId, newPrice);
        if (newPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Cannot update price. new price less zero");
        }
//        ProductDto productDto = getProductById(productId);
        int res = productRepository.updatePrice(productId, newPrice);
        if (res == 0) {
            throw new IllegalArgumentException("что то не так при обновлении цены");
        }
        log.info("Price updated success [product_id = {}] [new price =  {}]", productId, newPrice);
        ProductPriceUpdateEvent event = ProductPriceUpdateEvent.builder()
                .productId(productId)
                .newPrice(newPrice)
                .eventTime(LocalDateTime.now().toString())
                .build();

        kafkaTemplate.send(updatePriceEventTopic,
                productId.toString(),
                defaultObjectMapper.writeValueAsString(event));
        log.info("send event to topic [{}]", updatePriceEventTopic);
    }
}
