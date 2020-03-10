package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface INSDService {

    Mono<ResultBO> getListOfNSDs(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getNSDDetail(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> createNSD(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> uploadContentNSD(String path, RequestContent requestContent, long contentLenght) throws IOException;

    Mono<Resource> downloadContentNSD(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> updateNSD(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteNSD(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getTotalNsd(String path, RequestContent requestContent) throws IOException;

}
