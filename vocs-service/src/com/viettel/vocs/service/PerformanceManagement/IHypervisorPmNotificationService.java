package com.viettel.vocs.service.PerformanceManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IHypervisorPmNotificationService {

	Mono<ResultBO> getListOfHypervisorPmNotification(String path, String valueInput) throws IOException;

}
