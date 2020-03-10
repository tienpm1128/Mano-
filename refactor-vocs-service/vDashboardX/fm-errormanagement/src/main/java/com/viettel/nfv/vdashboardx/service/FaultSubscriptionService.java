package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Mono;

@Service
public class FaultSubscriptionService implements IFaultSuscriptionService {

	@Autowired
	IGetListObjectService getListObjectService;

	@Autowired
	IGetDetailObjectService getDetailObjectService;

	@Autowired
	ICreateObjectService createObjectService;
	
	@Autowired
	IDeleteObjectService deleteObjectService;

	@Override
	public Mono<ResultBO> getListOfFaultSubscription(String path, RequestContent requestContent) throws IOException {
		return getListObjectService.getArrayList(path, requestContent);
	}

	@Override
	public Mono<ResultBO> createFaultSubscription(String path, RequestContent requestContent) throws IOException {
		return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
	}

	@Override
	public Mono<ResultBO> getFaultSubscriptionDetail(String path, RequestContent requestContent) throws IOException {
		return getDetailObjectService.getObject(path, requestContent);
	}

	@Override
	public Mono<ResultBO> deleteFaultSubscription(String path, RequestContent requestContent) throws IOException {
		return deleteObjectService.deleteObject(path, requestContent);
	}

}
