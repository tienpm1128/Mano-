package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface ITenantService {

    Mono<ResultBO> getListOfTenants(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getTenantDetail(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> createTenant(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteTenant(String path, RequestContent requestContent) throws IOException;

}
