package com.viettel.vocs.service.ErrorManagement;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.vocs.bo.ResultBO;
import com.viettel.vocs.service.Common.IGetListObjectService;

import reactor.core.publisher.Mono;

@Service
public class AlarmNotificationService implements IAlarmNotificationService {

	@Autowired
	IGetListObjectService getListOfService;

	@Override
	public Mono<ResultBO> getListOfAlarmNotifications(String path, String valueInput) throws IOException {
		return getListOfService.getArrayList(path, valueInput);
	}

}
