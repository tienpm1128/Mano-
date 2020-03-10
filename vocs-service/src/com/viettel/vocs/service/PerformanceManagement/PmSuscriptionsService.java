package com.viettel.vocs.service.PerformanceManagement;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.vocs.bo.ResultBO;
import com.viettel.vocs.service.Common.ICreateObjectService;
import com.viettel.vocs.service.Common.IDeleteObjectService;
import com.viettel.vocs.service.Common.IGetDetailObjectService;
import com.viettel.vocs.service.Common.IGetListObjectService;

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
	public Mono<ResultBO> getListOfPmSubscriptions(String path, String valueInput) throws IOException {
		return this.getListObjectService.getArrayList(path, valueInput);
	}

	@Override
	public Mono<ResultBO> createPmSubscription(String path, String valueInput) throws IOException {
		return this.createObjectService.postObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> getPmSubscriptionDetail(String path, String valueInput) throws IOException {
		return this.getDetailObjectService.getObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> deletePmSubscription(String path) throws IOException {
		return this.deleteObjectService.deleteObject(path);
	}

}
