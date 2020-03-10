package com.viettel.nfv.vdashboardx.authentication;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.Data;

/**
 *
 * @author ard333
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
@JsonPropertyOrder({ "access_token", "token_type", "expires_in", "role" })
public class AuthResponse {

	@JsonProperty("access_token")
	private String accessToken;

	@JsonProperty("token_type")
	private String tokenType;

	@JsonProperty("expires_in")
	private long expiresIn;

	@JsonProperty("role")
	private List<RoleUserAut> lstRole;

}
