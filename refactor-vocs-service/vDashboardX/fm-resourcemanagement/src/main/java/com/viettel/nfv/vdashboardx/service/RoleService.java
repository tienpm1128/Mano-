package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
public class RoleService implements IRoleService {

    @Autowired
    private IGetListObjectService getListObjectService;

    @Autowired
    private IGetDetailObjectService getDetailObjectService;

    @Autowired
    private IUpdateAllObjectService updateAllObjectService;

    @Autowired
    private IDeleteObjectService deleteObjectService;

    @Autowired
    private ICreateObjectService createObjectService;

    @Override
    public Mono<ResultBO> getListOfRoles(String path, RequestContent requestContent) throws IOException {
        return getListObjectService.getArrayList(path, requestContent);
    }

    @Override
    public Mono<ResultBO> getRoleDetail(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> updateRole(String path, RequestContent requestContent) throws IOException {
        return updateAllObjectService.putObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> deleteRole(String path, RequestContent requestContent) throws IOException {
        return deleteObjectService.deleteObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> createRole(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
    }
}
