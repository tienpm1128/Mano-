package com.viettel.vocs.service.ErrorManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IFaultSuscriptionService {

	Mono<ResultBO> getListOfFaultSubscription(String path, String valueInput) throws IOException;
	
	Mono<ResultBO> createFaultSubscription(String path, String valueInput) throws IOException;
	
	Mono<ResultBO> getFaultSubscriptionDetail(String path, String valueInput) throws IOException;
	
	Mono<ResultBO> deleteFaultSubscription(String path) throws IOException;
}
