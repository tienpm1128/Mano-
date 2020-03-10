package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
public class DownloadFileService implements IDownloadFileService {

    @Autowired
    private RequestUtils requestUtils;

    @Override
    public Mono<Resource> downloadFile(String path, RequestContent requestContent) throws IOException {
        return requestUtils.downloadFileRequest(path, requestContent);
    }
}
