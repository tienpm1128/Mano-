package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import com.viettel.nfv.vdashboardx.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class UpdatePartObjectService implements IUpdatePartObjectService {

	@Autowired
	RequestUtils requestUtils;

	@Override
	public Mono<ResultBO> patchObject(String path, RequestContent requestContent) throws IOException {
		return this.requestUtils.patchRequest(path, requestContent, ResultType.OBJECT_TYPE);
	}

}
