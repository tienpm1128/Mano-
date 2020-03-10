package com.viettel.vocs.service;

import java.io.IOException;

import com.viettel.vocs.bo.ResultObjectBO;

import reactor.core.publisher.Mono;

public interface IUsersService {

	Mono<ResultObjectBO> login(String value) throws IOException;
}
