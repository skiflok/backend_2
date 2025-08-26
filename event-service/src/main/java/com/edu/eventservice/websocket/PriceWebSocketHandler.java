package com.edu.eventservice.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component("priceHandler")
public class PriceWebSocketHandler implements WebSocketHandler {

    private final Sinks.Many<String> updatePriceSseSink;

    public PriceWebSocketHandler(Sinks.Many<String> updatePriceSseSink) {
        this.updatePriceSseSink = updatePriceSseSink;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                updatePriceSseSink.asFlux()
                        .map(session::textMessage)
        );
    }
}
