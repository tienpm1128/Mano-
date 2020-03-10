package com.viettel.vocs.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;

import reactor.core.publisher.EmitterProcessor;

@Service
public class NotificationsService {

	private static Logger logger = LogManager.getLogger(NotificationsService.class);
	
	private final EmitterProcessor<ServerSentEvent<String>> emitters = EmitterProcessor.create();
	
	public EmitterProcessor<ServerSentEvent<String>> getEmitters() {
		return emitters;
	}

	public void broadcastNotification(String notification) {
		try {
			logger.info("Notification will be broadcasted: " + notification);
			emitters.onNext(ServerSentEvent.builder(notification).build());
		} catch (Exception e) {
			emitters.onError(e);
		}
	}

}
