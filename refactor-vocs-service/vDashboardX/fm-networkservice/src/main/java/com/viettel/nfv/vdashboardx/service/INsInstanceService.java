package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface INsInstanceService {

    Mono<ResultBO> getListOfNsInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getNsInstanceDetail(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> createNsInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteNsInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getTotalNsInstance(String path, RequestContent requestContent) throws IOException;

}
