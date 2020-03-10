package com.viettel.vocs.service.Common;

import java.io.IOException;

import com.viettel.vocs.bo.ResultBO;

import reactor.core.publisher.Mono;

public interface IUpdatePartObjectService {

	Mono<ResultBO> patchObject(String path, Object obj) throws IOException;

}
