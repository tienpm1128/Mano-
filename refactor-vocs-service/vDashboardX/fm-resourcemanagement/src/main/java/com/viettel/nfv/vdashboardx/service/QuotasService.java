package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
public class QuotasService implements IQuotasService {

    @Autowired
    private IGetDetailObjectService getDetailObjectService;

    @Override
    public Mono<ResultBO> viewComputeQuota(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> viewComputeQuotaLeft(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> viewNetworkQuota(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> viewNetworkQuotaLeft(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> viewResourceQuota(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> viewStorageQuota(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }

    @Override
    public Mono<ResultBO> viewStorageQuotaLeft(String path, RequestContent requestContent) throws IOException {
        return getDetailObjectService.getObject(path, requestContent);
    }


}
