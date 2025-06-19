package com.edu.eventservice.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@Component("stockHandler")
public class StockWebSocketHandler implements WebSocketHandler {

    private final Sinks.Many<String> updateStockSseSink;

    public StockWebSocketHandler(Sinks.Many<String> updateStockSseSink) {
        this.updateStockSseSink = updateStockSseSink;
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        return session.send(
                updateStockSseSink.asFlux()
                        .map(session::textMessage)
        );
    }
}
