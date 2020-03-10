package com.viettel.nfv.vdashboardx.controller;

import com.viettel.nfv.vdashboardx.communication.ISSEController;
import com.viettel.nfv.vdashboardx.service.NotificationsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@PropertySource(value = "classpath:/ws-notifications.properties", ignoreResourceNotFound = true)
@RequestMapping("${vocs-service.common}")
public class SSEController implements ISSEController {

	
	@Autowired
	private NotificationsService notificationService;

	@Override
	public Flux<ServerSentEvent<String>> sendNotificationSse() {
		return this.notificationService.getEmitter().publish().autoConnect();
	}

}
