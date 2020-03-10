package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

public interface IHypervisorThresholdService {

	Mono<ResultBO> getListOfHypervisorThreshold(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getHypervisorThresholdDetail(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> createHypervisorThreshold(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> deleteHypervisorThreshold(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getThresholdDetailByHypervisorId(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getThresholdDetailByVimId(String path, RequestContent requestContent) throws IOException;
}
