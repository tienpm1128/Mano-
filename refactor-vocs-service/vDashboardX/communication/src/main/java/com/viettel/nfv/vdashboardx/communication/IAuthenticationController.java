package com.viettel.nfv.vdashboardx.communication;

import com.viettel.nfv.vdashboardx.authentication.AuthRequest;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IAuthenticationController {

    @PostMapping(value = "${authentication.login}")
    default Mono<ResultBO> loginApi(@RequestBody AuthRequest ar, WebSession session) throws IOException {
        return login(ar, session);
    }

    Mono<ResultBO> login(AuthRequest ar, WebSession session) throws IOException;

    @GetMapping(value = "${authentication.logout}")
    default void logoutApi(ServerWebExchange exchange) {
        logout(exchange);
    }

    void logout(ServerWebExchange exchange);
}
