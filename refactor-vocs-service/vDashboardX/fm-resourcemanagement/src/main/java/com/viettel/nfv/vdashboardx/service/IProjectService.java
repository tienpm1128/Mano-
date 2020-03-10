package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IProjectService {

    Mono<ResultBO> getListOfProjectsByUserId(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> createProject(String pathh, RequestContent requestContent) throws IOException;

    Mono<ResultBO> assignUserToProject(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> getProjectDetail(String pathh, RequestContent requestContent) throws IOException;

    Mono<ResultBO> updateProject(String pathh, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteProject(String path, RequestContent requestContent) throws IOException;

}
