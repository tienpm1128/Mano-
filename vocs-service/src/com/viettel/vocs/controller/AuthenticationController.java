package com.viettel.vocs.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;

import com.google.gson.Gson;
import com.viettel.vocs.bo.ErrHttpCode;
import com.viettel.vocs.dto.AuthRequest;
import com.viettel.vocs.service.UsersService;
import com.viettel.vocs.session.TokenUtil;
import com.viettel.vocs.utils.Constants;
import com.viettel.vocs.utils.RestUtils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vocs-service")
@CrossOrigin(origins = "http://localhost:8084")
public class AuthenticationController {

	private static Logger logger = LogManager.getLogger(AuthenticationController.class);
	
	@Value("${springbootwebfluxjjwt.jjwt.expiration}")
	private String expirationTime;

	@Value("${spring.path.json}")
	private String pathJson;

	@Autowired
	private UsersService usersService;

	@Autowired
	RestUtils restUtils;

	static final Gson gson = new Gson();
	TokenUtil tokenUtils;

	@RequestMapping(value = "/users/auth", method = RequestMethod.POST)
	public Mono<ResponseEntity<?>> login(@RequestBody AuthRequest ar, WebSession session) {
		logger.info("Authenticated information of user.");
		String json = gson.toJson(ar);
		session.getAttributes().putIfAbsent("SESSION_AUTH", json);
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes400(Constants.MESS_400);
		Constants.setErrHttpCode(errHttpCode);

		return usersService.login(json).map((userDetails) -> {
			if (null != userDetails) {
				session.getAttributes().putIfAbsent("SESSION_TOKEN", Constants.SESSION_TOKEN);
				session.getAttributes().putIfAbsent("USER_SESSION", ar);

				return ResponseEntity.ok(userDetails);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
		}).defaultIfEmpty(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
	}

	@RequestMapping(value = "/users/logout", method = RequestMethod.GET)
	public void logout(ServerWebExchange exchange) {
		logger.info("Log out successfully.");
		WebSession session = exchange.getSession().block();
		session.invalidate();
	}
}
