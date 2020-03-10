package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Mono;

@Service
public class AlarmService implements IAlarmService {

	@Autowired
	IGetListObjectService getListObjectService;

	@Autowired
	IGetDetailObjectService getDetailObjectService;
	
	@Autowired
	IUpdatePartObjectService updateObjectService;

	public Mono<ResultBO> getListOfAlarms(String path, RequestContent requestContent) throws IOException {
		return getListObjectService.getArrayList(path, requestContent);
	}

	public Mono<ResultBO> getAlarmDetail(String path, RequestContent requestContent) throws IOException {
		return getDetailObjectService.getObject(path, requestContent);
	}

	public Mono<ResultBO> updateAlarm(String path, RequestContent requestContent) throws IOException {
		return updateObjectService.patchObject(path, requestContent);
	}

}
