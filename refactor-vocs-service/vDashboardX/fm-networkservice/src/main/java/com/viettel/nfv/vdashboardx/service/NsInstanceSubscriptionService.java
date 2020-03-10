package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
public class NsInstanceSubscriptionService implements INsInstanceSubscriptionService {

    @Autowired
    private IGetListObjectService getListObjectService;

    @Autowired
    private IDeleteObjectService deleteObjectService;

    @Autowired
    private ICreateObjectService createObjectService;

    @Override
    public Mono<ResultBO> getListofNsInstanceSubscription(String path, RequestContent requestContent) throws IOException {
        return getListObjectService.getArrayList(path, requestContent);
    }

    @Override
    public Mono<ResultBO> deleteNsInstanceSubscriptionDetail(String path, RequestContent requestContent) throws IOException {
        return deleteObjectService.deleteObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> createNsInstanceSubscription(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
    }
}
