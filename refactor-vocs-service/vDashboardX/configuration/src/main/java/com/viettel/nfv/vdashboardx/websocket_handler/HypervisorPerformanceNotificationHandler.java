package com.viettel.nfv.vdashboardx.websocket_handler;

import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

public class HypervisorPerformanceNotificationHandler extends WsNotificationHandler implements WebSocketHandler {

    public HypervisorPerformanceNotificationHandler() {
        super();
    }

    @Override
    public Mono<Void> handle(WebSocketSession session) {
        this.handleSession(session);
        return session.send(this.outputMessages.map(session::textMessage));
    }
}