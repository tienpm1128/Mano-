package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import reactor.core.publisher.Mono;

import java.io.IOException;


public interface IUsersService {

	Mono<ResultBO> login(RequestContent requestContent, ResultType type) throws IOException;
}
