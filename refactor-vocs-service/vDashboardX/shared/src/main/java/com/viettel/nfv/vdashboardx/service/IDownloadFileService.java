package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import org.springframework.core.io.Resource;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IDownloadFileService {

    Mono<Resource> downloadFile(String path, RequestContent requestContent) throws IOException;

}
