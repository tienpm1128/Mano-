package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IUserManoService {

    Mono<ResultBO> getListOfUserManos(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getUserManoDetail(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> createUserMano(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> updateUserMano(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteUserMano(String path, RequestContent requestContent) throws IOException;

}
