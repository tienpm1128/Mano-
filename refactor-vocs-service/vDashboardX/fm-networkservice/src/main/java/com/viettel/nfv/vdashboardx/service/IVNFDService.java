package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IVNFDService {

    Mono<ResultBO> getListOfVNFDs(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getVNFDDetail(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getTotalVnfd(String path, RequestContent requestContent) throws IOException;

}
