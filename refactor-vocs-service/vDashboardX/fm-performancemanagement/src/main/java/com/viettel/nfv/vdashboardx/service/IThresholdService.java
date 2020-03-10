package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

public interface IThresholdService {

	Mono<ResultBO> getListOfThresholds(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> createThreshold(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getThresholdDetail(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> deleteThreshold(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getThresholdDetailByNsInstanceId(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getListOfThresholdsByVim(String path, RequestContent requestContent) throws IOException;

}
