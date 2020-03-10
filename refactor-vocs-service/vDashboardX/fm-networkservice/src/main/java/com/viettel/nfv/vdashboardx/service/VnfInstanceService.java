package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
public class VnfInstanceService implements IVnfInstanceService {

    @Autowired
    private IGetListObjectService getListObjectService;

    @Autowired
    private ICreateObjectService createObjectService;

    @Autowired
    private IDeleteObjectService deleteObjectService;

    @Autowired
    private IGetDetailObjectService getDetailObjectService;

    @Override
    public Mono<ResultBO> instantiateVnfInstance(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
    }

    @Override
    public Mono<ResultBO> scaleVnfInstance(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
    }

    @Override
    public Mono<ResultBO> terminateVnfInstance(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
    }

    @Override
    public Mono<ResultBO> deleteVnfInstance(String path, RequestContent requestContent) throws IOException {
        return deleteObjectService.deleteObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> getListOfVnfInstance(String path, RequestContent requestContent) throws IOException {
        return getListObjectService.getArrayList(path, requestContent);
    }

    @Override
    public Mono<ResultBO> getTotalVnfInstance(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> getVnfInstanceDetail(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }
}
