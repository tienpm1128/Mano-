package com.viettel.vocs.service.Common;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.vocs.bo.ResponseBO;
import com.viettel.vocs.bo.ResultBO;
import com.viettel.vocs.bo.ResultObjectBO;
import com.viettel.vocs.utils.ResponseUtils;
import com.viettel.vocs.utils.RestUtils;

import reactor.core.publisher.Mono;


@Service
public class UpdateObjectService implements IUpdateAllObjectService {

	@Autowired
	RestUtils restUtils;

	@Autowired
	ResponseUtils responseUtils;

	@Override
	public Mono<ResultBO> putObject(String path, String valueInput) throws IOException {
		ResponseBO dto = restUtils.putRequestObject(path, valueInput);
		ResultObjectBO objDto = responseUtils.resolveObject(dto);

		return Mono.just(objDto);
	}

}
