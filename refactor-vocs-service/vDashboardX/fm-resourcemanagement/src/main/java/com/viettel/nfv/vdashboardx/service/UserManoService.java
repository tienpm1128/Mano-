package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
public class UserManoService implements IUserManoService {

    @Autowired
    private IGetListObjectService getListObjectService;

    @Autowired
    private IGetDetailObjectService getDetailObjectService;

    @Autowired
    private IUpdateAllObjectService updateAllObjectService;

    @Autowired
    private ICreateObjectService createObjectService;

    @Autowired
    private IDeleteObjectService deleteObjectService;

    @Override
    public Mono<ResultBO> getListOfUserManos(String path, RequestContent requestContent) throws IOException {
        return getListObjectService.getArrayList(path, requestContent);
    }

    @Override
    public Mono<ResultBO> getUserManoDetail(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> createUserMano(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
    }

    @Override
    public Mono<ResultBO> updateUserMano(String path, RequestContent requestContent) throws IOException {
        return updateAllObjectService.putObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> deleteUserMano(String path, RequestContent requestContent) throws IOException {
        return deleteObjectService.deleteObject(path, requestContent);
    }

}
