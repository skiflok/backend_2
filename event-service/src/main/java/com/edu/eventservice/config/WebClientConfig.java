package com.edu.eventservice.config;

import com.edu.eventservice.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

    private final JwtTokenProvider tokenProvider;

    @Bean
    public WebClient shopWebClient(WebClient.Builder builder,
                                   @Value("${rest.client.shop-service.product.all.url}") String baseUrl,
                                   JwtTokenProvider jwtProvider) {
        return builder
                .baseUrl(baseUrl)
                .filter((request, next) ->
                        Mono.fromCallable(jwtProvider::getToken)   // оборачиваем String в Mono<String>
                                .flatMap(token -> {
                                    ClientRequest filtered = ClientRequest.from(request)
                                            .headers(h -> h.setBearerAuth(token))
                                            .build();
                                    return next.exchange(filtered);
                                })
                )
                .build();
    }
}
