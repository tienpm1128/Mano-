package com.viettel.vocs.bo;

import java.net.HttpURLConnection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseBO {

	private int errorCode;

	private HttpURLConnection connect;

	private String body;

}
