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
	public Mono<ResultBO> getListOfThresholds(String path, String valueInput) throws IOException {
		return getListOfObjectsService.getArrayList(path, valueInput);
	}

	@Override
	public Mono<ResultBO> createThreshold(String path, String valueInput) throws IOException {
		return createObjectService.postObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> getThresholdDetail(String path, String valueInput) throws IOException {
		return getDetailObjectService.getObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> deleteThreshold(String path) throws IOException {
		return deleteObjectService.deleteObject(path);
	}

	@Override
	public Mono<ResultBO> getThresholdDetailByNsInstanceId(String path, String valueInput) throws IOException {
		return getDetailObjectService.getObject(path, valueInput);
	}

	@Override
	public Mono<ResultBO> getListOfThresholdsByVim(String path, String valueInput) throws IOException {
		return this.getListOfThresholds(path, valueInput);
	}

}
