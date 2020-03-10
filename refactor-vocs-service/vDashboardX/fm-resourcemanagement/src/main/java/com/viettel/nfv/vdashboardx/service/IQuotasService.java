package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IQuotasService {

    Mono<ResultBO> viewComputeQuota(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> viewComputeQuotaLeft(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> viewNetworkQuota(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> viewNetworkQuotaLeft(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> viewResourceQuota(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> viewStorageQuota(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> viewStorageQuotaLeft(String path, RequestContent requestContent) throws IOException;

}
