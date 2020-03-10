package com.viettel.vocs.service.PerformanceManagement;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.vocs.bo.ResultBO;
import com.viettel.vocs.service.Common.IGetListObjectService;

import reactor.core.publisher.Mono;

@Service
public class PmNotificationsService implements IPmNotificationsService{

	@Autowired
	private IGetListObjectService getListOfObjectService;
	
	@Override
	public Mono<ResultBO> getListOfPmNotifications(String path, String valueInput) throws IOException {
		return this.getListOfObjectService.getArrayList(path, valueInput);
	}

}
