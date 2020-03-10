package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

public interface IHypervisorPmJobService {

	Mono<ResultBO> getListOfHypervisorPmJob(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> createHypervisorPmJob(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getHypervisorPmJobDetail(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> reportHyperPmJob(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> deleteHypervisorPmJob(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getPmJobDetailByHypervisorId(String path, RequestContent requestContent) throws IOException;

}
