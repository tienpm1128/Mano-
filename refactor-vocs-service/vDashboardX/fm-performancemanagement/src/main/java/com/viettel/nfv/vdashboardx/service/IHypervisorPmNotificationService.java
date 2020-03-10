package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

public interface IHypervisorPmNotificationService {

	Mono<ResultBO> getListOfHypervisorPmNotification(String path, RequestContent requestContent) throws IOException;

}
