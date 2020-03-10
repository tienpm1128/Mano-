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
	public Mono<ResultBO> getListOfHypervisorPmJob(String path, String valueInput) throws IOException {
		return getListObjectService.getArrayList(path, valueInput);
	}

	@Override
	public Mono<ResultBO> createHypervisorPmJob(String path, String valueInput) throws IOException {
		return createObjectService.postObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> getHypervisorPmJobDetail(String path, String valueInput) throws IOException {
		return getDetailObjectService.getObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> reportHyperPmJob(String path, String valueInput) throws IOException {
		return getDetailObjectService.getObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> deleteHypervisorPmJob(String path) throws IOException {
		return deleteObjectService.deleteObject(path);
	}

	@Override
	public Mono<ResultBO> getPmJobDetailByHypervisorId(String path, String valueInput) throws IOException {
		return getDetailObjectService.getObject(path, valueInput);
	}

}
