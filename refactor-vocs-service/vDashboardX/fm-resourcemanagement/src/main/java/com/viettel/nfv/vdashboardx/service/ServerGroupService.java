package com.viettel.nfv.vdashboardx.service;


import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class ServerGroupService implements IServerGroupService {

    @Autowired
    private IGetListObjectService getListObjectService;

    @Autowired
    private ICreateObjectService createObjectService;

    @Autowired
    private IDeleteObjectService deleteObjectService;

    @Override
    public Mono<ResultBO> getListOfServerGroups(String path, RequestContent requestContent) throws IOException {
        return getListObjectService.getArrayList(path, requestContent);
    }

    @Override
    public Mono<ResultBO> createServerGroup(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.VALUE_TYPE);
    }

    @Override
    public Mono<ResultBO> deleteServerGroup(String path, RequestContent requestContent) throws IOException {
        return deleteObjectService.deleteObject(path, requestContent);
    }
}
