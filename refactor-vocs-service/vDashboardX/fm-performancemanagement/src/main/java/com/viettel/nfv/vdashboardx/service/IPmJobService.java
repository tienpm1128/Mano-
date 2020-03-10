package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

public interface IPmJobService {

	Mono<ResultBO> getListOfPmJobs(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getPmJobDetail(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> createPmJob(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> deletePmJob(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> reportPmJo(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getPmJobDetailByNsInstanceId(String path, RequestContent requestContent) throws IOException;

}
