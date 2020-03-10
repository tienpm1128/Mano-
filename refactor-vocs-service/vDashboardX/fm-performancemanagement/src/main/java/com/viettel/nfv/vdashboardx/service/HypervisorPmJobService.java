package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class HypervisorPmJobService implements IHypervisorPmJobService {

	@Autowired
	private IGetListObjectService getListObjectService;

	@Autowired
	private IGetDetailObjectService getDetailObjectService;

	@Autowired
	private ICreateObjectService createObjectService;

	@Autowired
	private IDeleteObjectService deleteObjectService;

	@Override
	public Mono<ResultBO> getListOfHypervisorPmJob(String path, RequestContent requestContent) throws IOException {
		return getListObjectService.getArrayList(path, requestContent);
	}

	@Override
	public Mono<ResultBO> createHypervisorPmJob(String path, RequestContent requestContent) throws IOException {
		return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
	}

	@Override
	public Mono<ResultBO> getHypervisorPmJobDetail(String path, RequestContent requestContent) throws IOException {
		return getDetailObjectService.getObject(path, requestContent);
	}

	@Override
	public Mono<ResultBO> reportHyperPmJob(String path, RequestContent requestContent) throws IOException {
		return getDetailObjectService.getObject(path, requestContent);
	}

	@Override
	public Mono<ResultBO> deleteHypervisorPmJob(String path, RequestContent requestContent) throws IOException {
		return deleteObjectService.deleteObject(path, requestContent);
	}

	@Override
	public Mono<ResultBO> getPmJobDetailByHypervisorId(String path, RequestContent requestContent) throws IOException {
		return getDetailObjectService.getObject(path, requestContent);
	}
}
