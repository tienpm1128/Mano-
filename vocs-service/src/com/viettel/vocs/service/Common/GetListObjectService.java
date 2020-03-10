package com.viettel.vocs.service.Common;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettel.vocs.bo.ResponseBO;
import com.viettel.vocs.bo.ResultArrayBO;
import com.viettel.vocs.bo.ResultBO;
import com.viettel.vocs.utils.ResponseUtils;
import com.viettel.vocs.utils.RestUtils;

import reactor.core.publisher.Mono;

@Service
public class GetListObjectService implements IGetListObjectService {

	@Autowired
	RestUtils restUtils;

	@Autowired
	ResponseUtils responseUtils;

	@Override
	public Mono<ResultBO> getArrayList(String path, String valueInput) throws IOException {
		ResponseBO responseDto = restUtils.getRequest(path, valueInput);
		ResultArrayBO result = responseUtils.resolveArray(responseDto);

		return Mono.just(result);
	}

}
