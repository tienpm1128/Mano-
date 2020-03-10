package com.viettel.vocs.service;

import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.viettel.vocs.bo.ResponseBO;
import com.viettel.vocs.bo.ResultObjectBO;
import com.viettel.vocs.security.AuthenFilter;
import com.viettel.vocs.utils.ResponseUtils;
import com.viettel.vocs.utils.RestUtils;

import ch.qos.logback.classic.Logger;
import reactor.core.publisher.Mono;

@Service
public class UsersService implements IUsersService {

	private static final Logger log = (Logger) LoggerFactory.getLogger(AuthenFilter.class);

	@Value("${spring.path.json}")
	private String pathJson;

	@Autowired
	RestUtils restUtils;

	@Autowired
	ResponseUtils responseUtils;

	@Override
	public Mono<ResultObjectBO> login(String input) {
		try {
			ResponseBO dto = restUtils.postRequest("/users/auth", input);
			ResultObjectBO objDto;
			objDto = responseUtils.resolveObject(dto);

			return Mono.just(objDto);
		} catch (IOException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR!", e);
		}
	}
}
