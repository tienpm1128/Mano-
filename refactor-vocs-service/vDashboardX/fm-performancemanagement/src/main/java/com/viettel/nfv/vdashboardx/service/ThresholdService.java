package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Mono;

@Service
public class ThresholdService implements IThresholdService {

	@Autowired
	private IGetListObjectService getListOfObjectsService;

	@Autowired
	private IGetDetailObjectService getDetailObjectService;

	@Autowired
	private IDeleteObjectService deleteObjectService;

	@Autowired
	private ICreateObjectService createObjectService;

	@Override
	public Mono<ResultBO> getListOfThresholds(String path, RequestContent requestContent) throws IOException {
		return getListOfObjectsService.getArrayList(path, requestContent);
	}

	@Override
	public Mono<ResultBO> createThreshold(String path, RequestContent requestContent) throws IOException {
		return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
	}

	@Override
	public Mono<ResultBO> getThresholdDetail(String path, RequestContent requestContent) throws IOException {
		return getDetailObjectService.getObject(path, requestContent);
	}

	@Override
	public Mono<ResultBO> deleteThreshold(String path, RequestContent requestContent) throws IOException {
		return deleteObjectService.deleteObject(path, requestContent);
	}

	@Override
	public Mono<ResultBO> getThresholdDetailByNsInstanceId(String path, RequestContent requestContent) throws IOException {
		return getDetailObjectService.getObject(path, requestContent);
	}

	@Override
	public Mono<ResultBO> getListOfThresholdsByVim(String path, RequestContent requestContent) throws IOException {
		return this.getListOfThresholds(path, requestContent);
	}
}
