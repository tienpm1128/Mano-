package com.viettel.vocs.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @author ard333
 */
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class AuthRequest {

	private String username;

	private String password;

}
