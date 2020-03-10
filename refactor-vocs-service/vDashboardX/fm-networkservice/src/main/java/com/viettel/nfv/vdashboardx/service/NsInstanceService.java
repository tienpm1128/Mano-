package com.viettel.nfv.vdashboardx.service;


import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class NsInstanceService implements INsInstanceService{

    @Autowired
    private IGetListObjectService getListObjectService;

    @Autowired
    private IGetDetailObjectService getDetailObjectService;

    @Autowired
    private ICreateObjectService createObjectService;

    @Autowired
    private IDeleteObjectService deleteObjectService;

    @Override
    public Mono<ResultBO> getListOfNsInstance(String path, RequestContent requestContent) throws IOException {
        return getListObjectService.getArrayList(path, requestContent);
    }

    @Override
    public Mono<ResultBO> getNsInstanceDetail(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> createNsInstance(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
    }

    @Override
    public Mono<ResultBO> deleteNsInstance(String path, RequestContent requestContent) throws IOException {
        return deleteObjectService.deleteObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> getTotalNsInstance(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }
}
