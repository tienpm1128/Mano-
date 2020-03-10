package com.viettel.vocs.bo;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ErrHttpCode {

	// HTTP 2XX
	private String mes200;

	private String mes201;

	private String mes202;

	private String mes204;

	// HTTP 3XX
	private String mes303;

	// HTTP 4XX
	private String mes400;

	private String mes401;

	private String mes404;

	private String mes409;
}
