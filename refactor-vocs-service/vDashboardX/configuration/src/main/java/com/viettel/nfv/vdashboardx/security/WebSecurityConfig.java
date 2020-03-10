package com.viettel.nfv.vdashboardx.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

/**
 *
 * @author ard333
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

	@Bean
	public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
		return http
				.exceptionHandling()
				.authenticationEntryPoint((swe, e) ->
					 Mono.fromRunnable(() ->
						swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
					)
				)
				.accessDeniedHandler((swe, e) ->
					Mono.fromRunnable(() ->
						swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
					)
				).and()
				.csrf().disable()
				.formLogin().disable()
				.httpBasic().disable()
				.logout().disable()
				.authorizeExchange()
				.pathMatchers(HttpMethod.OPTIONS).permitAll()
				.pathMatchers("/com.viettel.vocs-service/users/auth","/com.viettel.vocs-service/users/logout").permitAll()
				.anyExchange().permitAll()
				.and().build();
	}
}
