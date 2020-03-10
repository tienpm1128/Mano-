package com.viettel.vocs.service.Common;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface ICreateObjectService {

	Mono<ResultBO> postObject(String path, String valueInput) throws IOException;

}
