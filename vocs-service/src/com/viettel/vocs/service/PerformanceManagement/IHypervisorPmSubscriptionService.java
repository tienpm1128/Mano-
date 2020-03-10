package com.viettel.vocs.service.PerformanceManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IHypervisorPmSubscriptionService {

	Mono<ResultBO> getListOfHypervisorPmSubscription(String path, String valueInput) throws IOException;

	Mono<ResultBO> getHypervisorPmSubscriptionDetail(String path, String valueInput) throws IOException;

	Mono<ResultBO> createHypervisorPmSubscription(String path, String valueInput) throws IOException;

	Mono<ResultBO> deleteHypervisorPmSubscription(String path) throws IOException;

}
