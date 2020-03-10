package com.viettel.nfv.vdashboardx.service;

import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import com.viettel.nfv.vdashboardx.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
public class UsersService implements IUsersService {

	@Value("${spring.path.json}")
	private String pathJson;

	@Autowired
	RequestUtils requestUtils;

	@Override
	public Mono<ResultBO> login(RequestContent requestContent, ResultType type) throws IOException {
			return this.requestUtils.postRequest("/users/auth", requestContent, type);
	}
}
