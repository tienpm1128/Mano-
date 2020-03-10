package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

public interface IFaultSuscriptionService {

	Mono<ResultBO> getListOfFaultSubscription(String path, RequestContent requestContent) throws IOException;
	
	Mono<ResultBO> createFaultSubscription(String path, RequestContent requestContent) throws IOException;
	
	Mono<ResultBO> getFaultSubscriptionDetail(String path, RequestContent requestContent) throws IOException;
	
	Mono<ResultBO> deleteFaultSubscription(String path, RequestContent requestContent) throws IOException;
}
