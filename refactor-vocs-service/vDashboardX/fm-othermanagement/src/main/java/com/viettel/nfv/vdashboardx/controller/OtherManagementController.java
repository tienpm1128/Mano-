package com.viettel.nfv.vdashboardx.controller;

import com.viettel.nfv.vdashboardx.communication.IOtherManagementController;
import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.service.IManoService;
import com.viettel.nfv.vdashboardx.service.ISupportingService;
import com.viettel.nfv.vdashboardx.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@PropertySource(value = "classpath:/ws-common-management.properties", ignoreResourceNotFound = true)
@RequestMapping("${vocs-service.common}")
@CrossOrigin(origins = "http://localhost:8084")
public class OtherManagementController implements IOtherManagementController {

	@Autowired
	private ISupportingService supportingService;

	@Autowired
	private IManoService manoService;

	/**
	 * 1.6.1. The list of Supporting Service
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfSupportingService(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.supportingService.getListOfSupportingService(path, requestContent);
	}

	/**
	 * 1.6.2. The list of Mano service
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfManoServices(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return manoService.getListOfManoService(path, requestContent);
	}

}
