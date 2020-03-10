package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

public interface IHypervisorPmSubscriptionService {

	Mono<ResultBO> getListOfHypervisorPmSubscription(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getHypervisorPmSubscriptionDetail(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> createHypervisorPmSubscription(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> deleteHypervisorPmSubscription(String path, RequestContent requestContent) throws IOException;

}
