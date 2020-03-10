package com.viettel.nfv.vdashboardx.service;

import java.io.IOException;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import reactor.core.publisher.Mono;

public interface IAlarmService {

	Mono<ResultBO> getListOfAlarms(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> getAlarmDetail(String path, RequestContent requestContent) throws IOException;

	Mono<ResultBO> updateAlarm(String path, RequestContent requestContent) throws IOException;

}
