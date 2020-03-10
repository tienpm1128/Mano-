package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
public class SoftwareService implements ISoftwareService {

    @Autowired
    private IGetListObjectService getListObjectService;

    @Autowired
    private IGetDetailObjectService getDetailObjectService;

    @Autowired
    private IUpdateAllObjectService updateAllObjectService;

    @Autowired
    private IUploadFileService uploadFileService;

    @Override
    public Mono<ResultBO> getListOfSoftwares(String path, RequestContent requestContent) throws IOException {
        return getListObjectService.getArrayList(path, requestContent);
    }

    @Override
    public Mono<ResultBO> getSoftwareDetail(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> updateSoftware(String path, RequestContent requestContent) throws IOException {
        return updateAllObjectService.putObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> uploadSoftware(String path, RequestContent requestContent, long fileSize) throws IOException {
        return uploadFileService.uploadFile(path, requestContent, ResultType.VALUE_TYPE, HttpMethod.POST);
    }
}
