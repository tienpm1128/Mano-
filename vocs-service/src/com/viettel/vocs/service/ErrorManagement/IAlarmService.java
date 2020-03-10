package com.viettel.vocs.service.ErrorManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IAlarmService {

	Mono<ResultBO> getListOfAlarms(String path, String valueInput) throws IOException;

	Mono<ResultBO> getAlarmDetail(String path, String valueInput) throws IOException;

	Mono<ResultBO> updateAlarm(String path, Object obj) throws IOException;
}
