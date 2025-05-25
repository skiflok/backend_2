package com.edu.eventservice.service;

import com.edu.eventservice.dto.ProductDto;
import com.edu.eventservice.dto.ProductUpdateEvent;
import com.edu.eventservice.utils.ProductUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventGeneratorService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${kafka.topic.out.product.updates-stocks}")
    private String PRODUCT_UPDATE_STOCKS_TOPIC;

    private int i = 0;


    /* todo для изменения количества (отдельный шедуллер)
        1 синхронный рест запрос на список товаров
        2 выбрать рандомный товар
        2 уменьшить товар на количество от 1 до 9 (если больше 10)
        3 увеличить на 200 если меньше 10
     */

    @Scheduled(fixedRate = 30000) // Запуск каждые 5 секунд
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

            ProductUpdateEvent event = new ProductUpdateEvent(
                    selected.getId(),
                    selected.getPrice(), // цена не меняется
                    newStock
//                    Instant.now()
            );
            kafka.send(PRODUCT_UPDATE_STOCKS_TOPIC,
                    String.valueOf(selected.getId()),
                    objectMapper.writeValueAsString(event));
            log.info("product [id={}] update stocks [from {} by {}]",
                    selected.getId(),
                    selected.getAvailableStock(),
                    event.getNewStock());
        } catch (Exception e) {
            log.error("Update stocks error", e);
        }
    }

    /* todo для изменения цены (отдельный шедуллер)
        1 синхронный рест запрос на список товаров
        2 выбрать рандомный товар
        3 выбрать процент изменения от 5 до 10 процентов
        4 выбрать уменьшение или увеличение
        5 нужно обработать вариант когда цена падает или возрастает слишком сильно
 */
//    @PostConstruct
    @Scheduled(fixedRate = 5000) // Запуск каждые 5 секунд
    private void generate() {
        sendMessage("hello " + i++);
    }

    public void sendMessage(String message) {
        kafkaTemplate.send("test", message);
        log.info("Сообщение отправлено: {}", message);
    }

    public List<ProductDto> getAllProduct() {
        //todo rest to shop
        return mockResponse();
    }

    private List<ProductDto> mockResponse() {
        ProductDto productDto = ProductDto.builder()
                .id(1L)
                .availableStock(5)
                .price(BigDecimal.valueOf(15.00))
                .build();

        return List.of(productDto);
    }
}
