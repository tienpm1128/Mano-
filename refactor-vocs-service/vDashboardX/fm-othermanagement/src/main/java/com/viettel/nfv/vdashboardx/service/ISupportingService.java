package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface ISupportingService {

	Mono<ResultBO> getListOfSupportingService(String path, RequestContent requestContent) throws IOException;

}
