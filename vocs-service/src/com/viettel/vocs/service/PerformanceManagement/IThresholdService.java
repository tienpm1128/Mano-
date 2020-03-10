package com.viettel.vocs.service.PerformanceManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IThresholdService {

	Mono<ResultBO> getListOfThresholds(String path, String valueInput) throws IOException;

	Mono<ResultBO> createThreshold(String path, String valueInput) throws IOException;

	Mono<ResultBO> getThresholdDetail(String path, String valueInput) throws IOException;

	Mono<ResultBO> deleteThreshold(String path) throws IOException;

	Mono<ResultBO> getThresholdDetailByNsInstanceId(String path, String valueInput) throws IOException;

	Mono<ResultBO> getListOfThresholdsByVim(String path, String valueInput) throws IOException;

}
