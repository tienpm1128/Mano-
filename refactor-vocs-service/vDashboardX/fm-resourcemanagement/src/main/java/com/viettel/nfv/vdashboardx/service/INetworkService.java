package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface INetworkService {

    Mono<ResultBO> getListOfNetworks(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getTotalNetworks(String path, RequestContent requestContent) throws IOException;

}
