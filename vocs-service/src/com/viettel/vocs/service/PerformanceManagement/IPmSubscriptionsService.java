package com.viettel.vocs.service.PerformanceManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IPmSubscriptionsService {

	Mono<ResultBO> getListOfPmSubscriptions(String path, String valueInput) throws IOException;

	Mono<ResultBO> createPmSubscription(String path, String valueInput) throws IOException;

	Mono<ResultBO> getPmSubscriptionDetail(String path, String valueInput) throws IOException;

	Mono<ResultBO> deletePmSubscription(String path) throws IOException;

}
