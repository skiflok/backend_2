package com.edu.eventservice.client;

import com.edu.eventservice.dto.ProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductClient {

    //    private final RestTemplate shopRestTemplate;
    private final WebClient webClient;
    @Value("${rest.client.shop-service.product.all.url}")
    private String productsUrl;


    public Mono<List<ProductDto>> getAllProducts() {
        return webClient.get()
                .uri(productsUrl)
                .retrieve()
                .bodyToFlux(ProductDto.class)
                .collectList();
    }
}
