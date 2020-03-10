package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import reactor.core.publisher.Mono;

@Service
public class PmNotificationsService implements IPmNotificationsService{

	@Autowired
	private IGetListObjectService getListOfObjectService;
	
	@Override
	public Mono<ResultBO> getListOfPmNotifications(String path, RequestContent requestContent) throws IOException {
		return this.getListOfObjectService.getArrayList(path, requestContent);
	}

}
