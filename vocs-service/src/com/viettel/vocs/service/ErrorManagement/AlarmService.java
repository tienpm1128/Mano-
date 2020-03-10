package com.viettel.vocs.service.ErrorManagement;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.vocs.bo.ResultBO;
import com.viettel.vocs.service.Common.IGetDetailObjectService;
import com.viettel.vocs.service.Common.IGetListObjectService;
import com.viettel.vocs.service.Common.IUpdatePartObjectService;

import reactor.core.publisher.Mono;

@Service
public class AlarmService implements IAlarmService {

	@Autowired
	IGetListObjectService getListObjectService;

	@Autowired
	IGetDetailObjectService getDetailObjectService;
	
	@Autowired
	IUpdatePartObjectService updateObjectService;

	public Mono<ResultBO> getListOfAlarms(String path, String valueInput) throws IOException {
		return getListObjectService.getArrayList(path, valueInput);
	}

	public Mono<ResultBO> getAlarmDetail(String path, String valueInput) throws IOException {
		return getDetailObjectService.getObject(path, valueInput);
	}

	public Mono<ResultBO> updateAlarm(String path, Object obj) throws IOException {
		return updateObjectService.patchObject(path, obj);
	}

}
