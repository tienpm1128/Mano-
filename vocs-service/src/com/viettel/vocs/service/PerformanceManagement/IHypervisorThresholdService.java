package com.viettel.vocs.service.PerformanceManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IHypervisorThresholdService {

	Mono<ResultBO> getListOfHypervisorThreshold(String path, String valueInput) throws IOException;

	Mono<ResultBO> getHypervisorThresholdDetail(String path, String valueInput) throws IOException;

	Mono<ResultBO> createHypervisorThreshold(String path, String valueInput) throws IOException;

	Mono<ResultBO> deleteHypervisorThreshold(String path) throws IOException;

	Mono<ResultBO> getThresholdDetailByHypervisorId(String path, String valueInput) throws IOException;

	Mono<ResultBO> getThresholdDetailByVimId(String path, String valueInput) throws IOException;
}
