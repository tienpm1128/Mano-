package com.viettel.vocs.security;

import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.session.config.annotation.web.server.EnableSpringWebSession;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import org.slf4j.LoggerFactory;
import com.viettel.vocs.utils.Constants;
import com.viettel.vocs.utils.RestUtils;

import ch.qos.logback.classic.Logger;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

@Configuration
@EnableWebFlux
@EnableSpringWebSession
public class AuthenFilter implements WebFilter {

	public static final String WHITE_LIST = "/vocs-service/users/auth";

	@Value("${springbootwebfluxjjwt.jjwt.expiration}")
	private String expireDate;

	@Autowired
	RestUtils restUtils;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		String traceId = UUID.randomUUID().toString();
		String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

		Constants.setKeyParamHeader(new HashMap<String, String>());
		if (StringUtils.isNotEmpty(authHeader)) {
			Constants.SESSION_TOKEN = authHeader;
		} else {
			Constants.SESSION_TOKEN = "";
		}

		chain.filter(exchange);

		return chain.filter(exchange).doAfterSuccessOrError((r, t) -> {
		}).subscriberContext(Context.of(String.class, traceId));
	}
}
