package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IImageService {

    Mono<ResultBO> getListOfImages(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> createImage(String path, RequestContent requestContent) throws IOException;

    Mono<ResultBO> deleteImage(String path, RequestContent requestContent) throws IOException;

}
