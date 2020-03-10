package com.viettel.vocs.service.Common;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IUpdateAllObjectService {

	Mono<ResultBO> putObject(String path, String valueInput) throws IOException;

}
