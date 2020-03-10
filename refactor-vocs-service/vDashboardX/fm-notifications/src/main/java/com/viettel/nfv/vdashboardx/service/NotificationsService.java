package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class NotificationsService {

	private static Logger logger = LoggerFactory.getLogger(NotificationsService.class.getName());

	private EmitterProcessor<ServerSentEvent<String>> emitterProcessor = EmitterProcessor.create();

	@Autowired
	private IGetDetailObjectService getDetailObjectService;


	public EmitterProcessor<ServerSentEvent<String>> getEmitter() {
		return emitterProcessor;
	}

	public void sendNotification(String notification) {
		try {
			logger.info("Notification will be sent: {}", notification);
			this.getEmitter().onNext(ServerSentEvent.builder(notification).build());
		} catch (Exception e) {
			logger.info("Error: {}", e);
		}
	}

	public Mono<ResultBO> getPerformanceReport(String path, RequestContent requestContent) throws IOException {
		return getDetailObjectService.getObject(path, requestContent);
	}

}
