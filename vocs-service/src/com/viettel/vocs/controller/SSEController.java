package com.viettel.vocs.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vocs.service.NotificationsService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = { "/vocs-service" })
public class SSEController {

	private static Logger logger = LogManager.getLogger(SSEController.class);
	
	@Autowired
	private NotificationsService notificationService;

	@GetMapping(value = { "/sse/notifications" }, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerSentEvent<String>> connectDashBoard() {
		logger.info("SSE connect to server.");
		return notificationService.getEmitters().log();
	}

}
