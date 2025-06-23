package com.edu.eventservice.service;

import com.edu.eventservice.client.ProductClient;
import com.edu.eventservice.dto.ProductDto;
import com.edu.eventservice.dto.event.ProductPriceUpdateEvent;
import com.edu.eventservice.dto.event.ProductStockUpdateEvent;
import com.edu.eventservice.utils.ProductEventMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventGeneratorService {

    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper defaultObjectMapper;
    private final ProductClient productClient;

    @Value("${kafka.out.product.updates-stocks.topic}")
    private String productUpdateStocksTopic;

    @Value("${kafka.out.product.updates-price.topic}")
    private String productUpdatePriceTopic;

    @Scheduled(fixedRateString = "${scheduler.product.updates-stocks:10000}")
    public void sendStockUpdate() {
        productClient.getAllProducts()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(this::processStockUpdate, e -> log.error("Failed to fetch products", e));
    }

    @Scheduled(fixedRateString = "${scheduler.product.updates-price:20000}")
    public void sendPriceUpdate() {
        productClient.getAllProducts()
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe(this::processPriceUpdate, e -> log.error("Failed to fetch products", e));
    }

    private void processStockUpdate(List<ProductDto> products) {
        ProductDto selected = ProductEventMapper.randomProduct(products);
        ProductStockUpdateEvent event = ProductEventMapper.generateStockEvent(selected);
        sendMessage(productUpdateStocksTopic, selected.getId(), event);
        log.info("Sent stock update: {}", event);
    }

    private void processPriceUpdate(List<ProductDto> products) {
        ProductDto selected = ProductEventMapper.randomProduct(products);
        ProductPriceUpdateEvent event = ProductEventMapper.generatePriceEvent(selected);
        sendMessage(productUpdatePriceTopic, selected.getId(), event);
        log.info("Sent price update: {}", event);
    }

    private void sendMessage(String topic, Long key, Object event) {
        try {
            String json = defaultObjectMapper.writeValueAsString(event);
            kafka.send(topic, String.valueOf(key), json);
        } catch (Exception e) {
            log.error("Kafka send failed", e);
        }
    }
}
