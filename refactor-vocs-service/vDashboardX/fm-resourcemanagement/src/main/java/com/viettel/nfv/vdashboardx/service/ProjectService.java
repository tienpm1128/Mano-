package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
public class ProjectService implements IProjectService {

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
    public Mono<ResultBO> getListOfProjectsByUserId(String path, RequestContent requestContent) throws IOException {
        return getListObjectService.getArrayList(path, requestContent);
    }

    @Override
    public Mono<ResultBO> createProject(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.OBJECT_TYPE);
    }

    @Override
    public Mono<ResultBO> assignUserToProject(String path, RequestContent requestContent) throws IOException {
        return createObjectService.postObject(path, requestContent, ResultType.ARRAY_TYPE);
    }

    @Override
    public Mono<ResultBO> getProjectDetail(String pathh, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(pathh, requestContent);
    }

    @Override
    public Mono<ResultBO> updateProject(String pathh, RequestContent requestContent) throws IOException {
        return updateAllObjectService.putObject(pathh, requestContent);
    }

    @Override
    public Mono<ResultBO> deleteProject(String path, RequestContent requestContent) throws IOException {
        return deleteObjectService.deleteObject(path, requestContent);
    }
}
