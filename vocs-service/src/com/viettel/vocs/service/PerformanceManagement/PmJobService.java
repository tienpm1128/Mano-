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
public class PmJobService implements IPmJobService {

	@Autowired
	private IGetListObjectService getListObjectService;

	@Autowired
	private IGetDetailObjectService getDetailObjectService;

	@Autowired
	private ICreateObjectService createObjectService;

	@Autowired
	private IDeleteObjectService deleteObjectService;

	@Override
	public Mono<ResultBO> getListOfPmJobs(String path, String valueInput) throws IOException {
		return getListObjectService.getArrayList(path, valueInput);
	}

	@Override
	public Mono<ResultBO> getPmJobDetail(String path, String valueInput) throws IOException {
		return getDetailObjectService.getObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> createPmJob(String path, String valueInput) throws IOException {
		return createObjectService.postObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> deletePmJob(String path) throws IOException {
		return deleteObjectService.deleteObject(path);
	}

	@Override
	public Mono<ResultBO> reportPmJo(String path, String valueInput) throws IOException {
		return getListObjectService.getArrayList(path, valueInput);
	}

	@Override
	public Mono<ResultBO> getPmJobDetailByNsInstanceId(String path, String valueInput) throws IOException {
		return getDetailObjectService.getObject(path, valueInput);
	}

}
