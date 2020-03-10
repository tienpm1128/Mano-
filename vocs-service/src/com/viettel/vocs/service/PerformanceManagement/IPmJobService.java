package com.viettel.vocs.service.PerformanceManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IPmJobService {

	Mono<ResultBO> getListOfPmJobs(String path, String valueInput) throws IOException;

	Mono<ResultBO> getPmJobDetail(String path, String valueInput) throws IOException;

	Mono<ResultBO> createPmJob(String path, String valueInput) throws IOException;

	Mono<ResultBO> deletePmJob(String path) throws IOException;

	Mono<ResultBO> reportPmJo(String path, String valueInput) throws IOException;

	Mono<ResultBO> getPmJobDetailByNsInstanceId(String path, String valueInput) throws IOException;

}
