package com.viettel.vocs.service.PerformanceManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IHypervisorPmJobService {

	Mono<ResultBO> getListOfHypervisorPmJob(String path, String valueInput) throws IOException;

	Mono<ResultBO> createHypervisorPmJob(String path, String valueInput) throws IOException;

	Mono<ResultBO> getHypervisorPmJobDetail(String path, String valueInput) throws IOException;

	Mono<ResultBO> reportHyperPmJob(String path, String valueInput) throws IOException;

	Mono<ResultBO> deleteHypervisorPmJob(String path) throws IOException;

	Mono<ResultBO> getPmJobDetailByHypervisorId(String path, String valueInput) throws IOException;

}
