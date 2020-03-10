package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IUploadFileService {

    Mono<ResultBO> uploadFile(String path, RequestContent requestContent, ResultType type, HttpMethod method) throws IOException;

}
