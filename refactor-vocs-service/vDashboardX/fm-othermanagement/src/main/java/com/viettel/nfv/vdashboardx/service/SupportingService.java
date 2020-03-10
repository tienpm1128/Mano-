package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
public class SupportingService implements ISupportingService {

	@Autowired
    IGetListObjectService getListObjectService;

	@Override
	public Mono<ResultBO> getListOfSupportingService(String path, RequestContent requestContent) throws IOException {
		return getListObjectService.getArrayList(path, requestContent);
	}

}
