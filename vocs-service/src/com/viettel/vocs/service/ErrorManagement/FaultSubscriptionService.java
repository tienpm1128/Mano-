package com.viettel.vocs.service.ErrorManagement;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.vocs.bo.ResponseBO;
import com.viettel.vocs.bo.ResultBO;
import com.viettel.vocs.bo.ResultObjectBO;
import com.viettel.vocs.service.Common.ICreateObjectService;
import com.viettel.vocs.service.Common.IDeleteObjectService;
import com.viettel.vocs.service.Common.IGetDetailObjectService;
import com.viettel.vocs.service.Common.IGetListObjectService;

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
	public Mono<ResultBO> getListOfFaultSubscription(String path, String valueInput) throws IOException {
		return getListObjectService.getArrayList(path, valueInput);
	}

	@Override
	public Mono<ResultBO> createFaultSubscription(String path, String valueInput) throws IOException {
		return createObjectService.postObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> getFaultSubscriptionDetail(String path, String valueInput) throws IOException {
		return getDetailObjectService.getObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> deleteFaultSubscription(String path) throws IOException {
		return deleteObjectService.deleteObject(path);
	}

}
