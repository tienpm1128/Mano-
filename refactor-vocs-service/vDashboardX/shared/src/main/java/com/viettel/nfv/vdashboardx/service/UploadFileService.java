package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import com.viettel.nfv.vdashboardx.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
public class UploadFileService implements IUploadFileService {

    @Autowired
    RequestUtils requestUtils;

    @Override
    public Mono<ResultBO> uploadFile(String path, RequestContent requestContent, ResultType type, HttpMethod method) throws IOException {
        return requestUtils.uploadFileRequest(path, requestContent, type, method);
    }
}
