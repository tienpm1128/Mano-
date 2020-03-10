package com.viettel.nfv.vdashboardx.controller;

import com.google.gson.Gson;
import com.viettel.nfv.vdashboardx.authentication.AuthRequest;
import com.viettel.nfv.vdashboardx.communication.IAuthenticationController;
import com.viettel.nfv.vdashboardx.exception.ErrHttpCode;
import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultType;
import com.viettel.nfv.vdashboardx.service.UsersService;
import com.viettel.nfv.vdashboardx.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@PropertySource(value = "classpath:/ws-authentication.properties", ignoreResourceNotFound = true)
@RequestMapping("${vocs-service.common}")
@CrossOrigin(origins = "http://localhost:8084")
public class AuthenticationController implements IAuthenticationController {

	private static Logger logger = LoggerFactory.getLogger(AuthenticationController.class.getName());
	
	@Value("${session.expiration}")
	private String expirationTime;

	@Value("${spring.path.json}")
	private String pathJson;

	@Autowired
	private UsersService usersService;

	private static final Gson gson = new Gson();

	public Mono<ResultBO> login(AuthRequest ar, WebSession session) throws IOException {
		logger.info("Authenticated information of user.");
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes400(Constants.MESS_400);

		String json = gson.toJson(ar);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer("")
				.errorCodes(errHttpCode)
				.bodyData(json)
				.build();

		return usersService.login(requestContent, ResultType.OBJECT_TYPE).map(userDetails -> {
			if (null != userDetails) {
				session.getAttributes().putIfAbsent("USER_SESSION", ar);
			}

			return userDetails;
		});
	}

	public void logout(ServerWebExchange exchange) {
		logger.info("Log out successfully.");
		WebSession session = exchange.getSession().block();
		session.invalidate();
	}

}
