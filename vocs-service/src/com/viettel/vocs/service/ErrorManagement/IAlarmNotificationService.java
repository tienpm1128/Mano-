package com.viettel.vocs.service.ErrorManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IAlarmNotificationService {

	Mono<ResultBO> getListOfAlarmNotifications(String path, String valueInput) throws IOException;

}
