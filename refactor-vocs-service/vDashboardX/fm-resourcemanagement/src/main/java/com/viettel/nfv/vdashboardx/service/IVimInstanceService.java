package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IVimInstanceService {

    Mono<ResultBO> getListOfVimInstances(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> createVimInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> loginVimInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getVimInstanceDetail(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> receiveVimNotification(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> authenticateVimUser(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteVim(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> logoutVimInstance(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getListOfVimsByHypervisorId(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> assignVimForUser(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getTotalVims(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getListOfUserResourceQuota(String path, RequestContent requestContent) throws IOException;

}
