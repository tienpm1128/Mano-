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
public class GetDetailObjectService implements IGetDetailObjectService {

	@Autowired
	RequestUtils requestUtils;

	@Override
	public Mono<ResultBO> getObject(String path, RequestContent requestContent) throws IOException {
		return this.requestUtils.getRequest(path, ResultType.OBJECT_TYPE, requestContent);
	}
}
