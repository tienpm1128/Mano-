package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IOpenstackUserService {

    Mono<ResultBO> getListOfOpenstackUser(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getOpenstackUserDetail(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> createOpenstackUser(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> updateOpenstackUser(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteOpenstackUser(String path, RequestContent requestContent) throws IOException;

}
