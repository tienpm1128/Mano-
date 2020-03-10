package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface INsInstanceSubscriptionService {

    Mono<ResultBO> getListofNsInstanceSubscription(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteNsInstanceSubscriptionDetail(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> createNsInstanceSubscription(String path, RequestContent requestContent) throws IOException;

}
