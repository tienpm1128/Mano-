package com.viettel.vocs.session;

import org.springframework.web.server.WebSession;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class TokenUtil {

	private String token;

	public TokenUtil(WebSession session) {
		token = session.getAttribute("SESSION_TOKEN");
	}

}
