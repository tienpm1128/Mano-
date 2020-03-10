package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IRoleService {

    Mono<ResultBO> getListOfRoles(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getRoleDetail(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> updateRole(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteRole(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> createRole(String path, RequestContent requestContent) throws IOException;

}
