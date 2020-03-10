package com.viettel.nfv.vdashboardx.websocket_handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;

public abstract class WsNotificationHandler {

    protected EmitterProcessor<String> messagePublisher = EmitterProcessor.create();

    protected Flux<String> outputMessages = messagePublisher.log();

    protected WebSocketMessageSubscriber subscriber = new WebSocketMessageSubscriber(this.messagePublisher);

    private static Logger logger = LoggerFactory.getLogger(WsNotificationHandler.class.getName());


    public WsNotificationHandler() {
        // nothing to do
    }

    public void sendMessage(String json) {
        this.subscriber.onNext(json);
    }

    public void handleSession(WebSocketSession session) {
        session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .subscribe(subscriber::onNext, subscriber::onError, subscriber::onComplete);
    }

    public static class WebSocketMessageSubscriber {

        private EmitterProcessor<String> messagePublisher;

        public WebSocketMessageSubscriber(EmitterProcessor<String> messagePublisher) {
            this.messagePublisher = messagePublisher;
        }

        public void onNext(String message) {
            messagePublisher.onNext(message);
        }

        public void onError(Throwable error) {
            logger.info(error.getMessage());
        }

        public void onComplete() {
            logger.info("Completed!");
        }
    }

}
