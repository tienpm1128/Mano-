package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import reactor.core.publisher.Mono;

public interface ICreateObjectService {

	Mono<ResultBO> postObject(String path, RequestContent requestContent, ResultType type) throws IOException;

}
