package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;


@Service
public class NsInstanceNotificationService implements INsInstanceNotificationService {

    @Autowired
    private IGetListObjectService getListObjectService;

    @Override
    public Mono<ResultBO> getListOfNsInstanceNotification(String path, RequestContent requestContent) throws IOException {
        return getListObjectService.getArrayList(path, requestContent);
    }
}
