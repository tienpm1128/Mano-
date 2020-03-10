package com.viettel.vocs.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vocs.service.ServiceManagement.IManoService;
import com.viettel.vocs.service.ServiceManagement.ISupportingService;
import com.viettel.vocs.utils.DataUtils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vocs-service")
@CrossOrigin(origins = "http://localhost:8084")
public class CommonController {

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
	@GetMapping(value = { "/api/v1/services/supporting" })
	@ResponseBody
	public Mono<ResponseEntity<?>> getListOfSupportingService(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.supportingService.getListOfSupportingService(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.6.2. The list of Mano service
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/api/v1/services/mano" })
	@ResponseBody
	Mono<ResponseEntity<?>> getListOfManoServices(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return manoService.getListOfManoService(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

}
