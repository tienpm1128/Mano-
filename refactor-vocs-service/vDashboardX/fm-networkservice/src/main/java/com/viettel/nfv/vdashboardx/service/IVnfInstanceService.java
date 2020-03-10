package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IVnfInstanceService {

    Mono<ResultBO> instantiateVnfInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> scaleVnfInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> terminateVnfInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteVnfInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getListOfVnfInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getTotalVnfInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getVnfInstanceDetail(String path, RequestContent requestContent) throws IOException;

}
