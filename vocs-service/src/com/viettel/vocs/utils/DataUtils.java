package com.viettel.vocs.utils;

import org.json.simple.JSONObject;

public class DataUtils {

	public static String repalcePath(String path) {
		return path.replace("/vocs-service", "");
	}

	public static String getMessage(String httpStatus, JSONObject jsonObj) {
		String mes = "";
		try {
			switch (httpStatus) {
			case "400":
				mes = (String) jsonObj.get("error_description");
				break;

			case "404":
				mes = (String) jsonObj.get("detail");
				break;

			case "409":
				mes = (String) jsonObj.get("detail");
				break;

			default:
				break;
			}
		} catch (Exception e) {
			return mes;
		}

		return mes;
	}
}
