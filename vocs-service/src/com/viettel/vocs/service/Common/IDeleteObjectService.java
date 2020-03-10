package com.viettel.vocs.service.Common;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IDeleteObjectService {

	Mono<ResultBO> deleteObject(String path) throws IOException;

}
