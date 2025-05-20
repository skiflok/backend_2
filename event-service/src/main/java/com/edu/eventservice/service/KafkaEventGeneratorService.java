package com.edu.eventservice.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEventGeneratorService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private int i = 0;


    /* todo для изменения количества (отдельный шедуллер)
        1 синхронный рест запрос на список товаров
        2 выбрать рандомный товар
        2 уменьшить товар на количество от 1 до 9 (если больше 10)
        3 увеличить на 200 если меньше 10
     */

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
}
