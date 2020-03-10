package com.viettel.vocs.bo;

import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientException;

import lombok.Data;

@Data
public class CustomClientException extends WebClientException {

	private static final long serialVersionUID = 1L;

	private final HttpStatus status;

	private final ErrorDetails details;

	public CustomClientException(HttpStatus status, ErrorDetails details) {
		super(status.getReasonPhrase());
		this.status = status;
		this.details = details;
	}

}
