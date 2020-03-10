package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

public interface IPmSubscriptionsService {

	Mono<ResultBO> getListOfPmSubscriptions(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> createPmSubscription(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getPmSubscriptionDetail(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> deletePmSubscription(String path, RequestContent requestContent) throws IOException;

}
