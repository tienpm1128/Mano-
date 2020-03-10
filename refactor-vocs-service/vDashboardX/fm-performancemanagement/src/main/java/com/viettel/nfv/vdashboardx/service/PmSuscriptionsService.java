package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class PmSuscriptionsService implements IPmSubscriptionsService {

	@Autowired
	private IGetListObjectService getListObjectService;

	@Autowired
	private ICreateObjectService createObjectService;

	@Autowired
	private IGetDetailObjectService getDetailObjectService;

	@Autowired
	private IDeleteObjectService deleteObjectService;

	@Override
	public Mono<ResultBO> getListOfPmSubscriptions(String path, RequestContent requestContent) throws IOException {
		return this.getListObjectService.getArrayList(path, requestContent);
	}

	@Override
	public Mono<ResultBO> createPmSubscription(String path, RequestContent requestContent) throws IOException {
		return this.createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
	}

	@Override
	public Mono<ResultBO> getPmSubscriptionDetail(String path, RequestContent requestContent) throws IOException {
		return this.getDetailObjectService.getObject(path, requestContent);
	}

	@Override
	public Mono<ResultBO> deletePmSubscription(String path, RequestContent requestContent) throws IOException {
		return this.deleteObjectService.deleteObject(path, requestContent);
	}

}
