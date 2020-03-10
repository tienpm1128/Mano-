package com.viettel.nfv.vdashboardx.utils;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataUtils {

	private DataUtils(){}

	public static String repalcePath(String path) {
		return path.replace("/com.viettel.vocs-service", "");
	}

	public static String getMessage(String httpStatus, JSONObject jsonObj) {
		String mes = "";
		try {
			switch (httpStatus) {
			case "400":
				mes = (String) jsonObj.get("error_description");
				break;

			case "404":
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

	/**
	 * Ex: http://localhost:9999/pm/extended/v1/internal_pm_jobs/0001/reports/0001
	 *
	 * @param url
	 * @return
	 */
	public static String getUri(String url) {
		String regex = "(?:(([a-z][a-z0-9+\\-.]*:(\\/\\/[^\\/?#]+)?)?))?(.+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);

		if (matcher.find()) {
			return matcher.group(4);
		}

		return "";
	}

	public static Map<String, String> createHeaderParams(String openStackUserId, String projectId) {
		HashMap<String, String> keyValueheader = new HashMap<>();
		keyValueheader.put(Constants.OPENSTACK_USERID, openStackUserId);
		keyValueheader.put(Constants.PROJECT_ID, projectId);

		return keyValueheader;
	}

}
