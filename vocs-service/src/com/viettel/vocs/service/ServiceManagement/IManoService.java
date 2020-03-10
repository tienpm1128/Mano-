package com.viettel.vocs.service.ServiceManagement;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IManoService {

	Mono<ResultBO> getListOfManoService(String path, String valueInput) throws IOException;

}
