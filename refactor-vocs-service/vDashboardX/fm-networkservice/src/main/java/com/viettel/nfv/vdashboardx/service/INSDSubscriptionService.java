package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface INSDSubscriptionService {

    Mono<ResultBO> getListOfNSDSubscriptions(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteNSDSubscription(String path, RequestContent requestContent) throws IOException;

}
