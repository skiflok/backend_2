package com.edu.eventservice.service;

import com.edu.eventservice.dto.ProductDto;
import com.edu.eventservice.dto.ProductUpdateEvent;
import com.edu.eventservice.utils.ProductUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class KafkaEventGeneratorService {

    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper defaultObjectMapper;

    private final String productUpdateStocksTopic;
    private final String productUpdatePriceTopic;

    //todo del
    private int availableStock = 5;

    public KafkaEventGeneratorService(
            KafkaTemplate<String, String> kafka,
            ObjectMapper defaultObjectMapper,
            @Value("${kafka.out.product.updates-stocks.topic}") String productUpdateStocksTopic,
            @Value("${kafka.out.product.updates-price.topic}") String productUpdatePriceTopic) {
        this.kafka = kafka;
        this.defaultObjectMapper = defaultObjectMapper;
        this.productUpdateStocksTopic = productUpdateStocksTopic;
        this.productUpdatePriceTopic = productUpdatePriceTopic;
    }

    @Scheduled(fixedRateString = "${scheduler.product.updates-stocks:10000}") // Запуск каждые 5 секунд
    private void changeAvailableStockByRandomProduct() {
        try {
            List<ProductDto> productDtoList = getAllProduct();
            ProductDto selected = ProductUtils.randomProduct(productDtoList);

            int currentStock = selected.getAvailableStock();
            int newStock;
            if (currentStock > 10) {
                newStock = currentStock - ThreadLocalRandom.current().nextInt(1, 10);
            } else {
                newStock = currentStock + 200;
            }
            //todo del
            availableStock = newStock;

            ProductUpdateEvent event = new ProductUpdateEvent(
                    selected.getId(),
                    selected.getPrice(), // цена не меняется
                    newStock,
                    Instant.now().toString()
            );
            kafka.send(productUpdateStocksTopic,
                    String.valueOf(selected.getId()),
                    defaultObjectMapper.writeValueAsString(event));
            log.info("product [id={}] update stocks [from {} by {}]",
                    selected.getId(),
                    selected.getAvailableStock(),
                    event.getNewStock());
        } catch (Exception e) {
            log.error("Update stocks error", e);
        }
    }

    @Scheduled(fixedRateString = "${scheduler.product.updates-price:20000}")
    private void changePriceByRandomProduct() {
        try {
            List<ProductDto> productDtoList = getAllProduct();
            ProductDto selected = ProductUtils.randomProduct(productDtoList);

            //todo переделать на более адекватное формирование цены
            BigDecimal newPrice = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(10.0, 20.0))
                    .setScale(2, RoundingMode.HALF_UP);

            ProductUpdateEvent event = new ProductUpdateEvent(
                    selected.getId(),
                    newPrice,
                    selected.getAvailableStock(),
                    Instant.now().toString()
            );
            kafka.send(productUpdatePriceTopic,
                    String.valueOf(selected.getId()),
                    defaultObjectMapper.writeValueAsString(event));
            log.info("product [id={}] update price [from {} by {}]",
                    selected.getId(),
                    selected.getPrice(),
                    event.getNewPrice());
        } catch (Exception e) {
            log.error("Update stocks error", e);
        }
    }

    public List<ProductDto> getAllProduct() {
        //todo rest to shop
        return mockResponse();
    }

    //todo del
    private List<ProductDto> mockResponse() {
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .availableStock(availableStock)
                .price(BigDecimal.valueOf(15.00))
                .build();

        return List.of(productDto);
    }
}
