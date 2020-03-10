package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface ISoftwareService {

    Mono<ResultBO> getListOfSoftwares(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getSoftwareDetail(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> updateSoftware(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> uploadSoftware(String path, RequestContent requestContent, long fileSize) throws IOException;

}
