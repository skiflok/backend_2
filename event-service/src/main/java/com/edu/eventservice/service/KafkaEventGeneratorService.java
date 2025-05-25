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
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class KafkaEventGeneratorService {

    private final KafkaTemplate<String, String> kafka;
    private final ObjectMapper defaultObjectMapper;

    private final String productUpdateStocksTopic;

    //todo del
    private int availableStock = 5;

    public KafkaEventGeneratorService(KafkaTemplate<String, String> kafka,
                                      ObjectMapper defaultObjectMapper,
                                      @Value("${kafka.out.product.updates-stocks.topic}") String productUpdateStocksTopic) {
        this.kafka = kafka;
        this.defaultObjectMapper = defaultObjectMapper;
        this.productUpdateStocksTopic = productUpdateStocksTopic;
    }


    /* todo для изменения количества (отдельный шедуллер)
        1 синхронный рест запрос на список товаров
        2 выбрать рандомный товар
        2 уменьшить товар на количество от 1 до 9 (если больше 10)
        3 увеличить на 200 если меньше 10
     */

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

    /* todo для изменения цены (отдельный шедуллер)
        1 синхронный рест запрос на список товаров
        2 выбрать рандомный товар
        3 выбрать процент изменения от 5 до 10 процентов
        4 выбрать уменьшение или увеличение
        5 нужно обработать вариант когда цена падает или возрастает слишком сильно
 */

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
