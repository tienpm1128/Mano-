package com.viettel.vocs.utils;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import ch.qos.logback.classic.Logger;

public class JsonUtils {

	private static final Logger logger = (Logger) LoggerFactory.getLogger(JsonUtils.class);
	static final Gson gson = new Gson();

	public static JSONObject ConvertStringToObject(String output) {
		JSONParser parser = new JSONParser();
		JSONObject json = null;

		if (StringUtils.isNotEmpty(output)) {
			try {
				json = (JSONObject) parser.parse(output);
			} catch (ParseException e) {
				logger.info("Can't pare String to JsonObject", e);
			}
		}

		return json;
	}

	public static JSONObject ReadFileByKey(JSONObject jsonObj, String key) {
		JSONObject companyList;
		JSONObject jsonObject = jsonObj;
		companyList = (JSONObject) jsonObject.get(key);

		return companyList;
	}

	public static JSONArray ConvertStringToJsonArray(String output) {
		JSONParser parser = new JSONParser();
		JSONArray companyList = new JSONArray();
		if (StringUtils.isNotEmpty(output)) {
			try {
				companyList = (JSONArray) parser.parse(output);
			} catch (Exception e) {
				logger.info("Can't pare String to JsonArray", e);
			}
		}

		return companyList;
	}

	public static <T extends Object> T ConvertJsonToObject(JSONObject jsonObj, Class<T> objClass) {
		Object object = new Object();
		byte[] jsonData = jsonObj.toString().getBytes();
		ObjectMapper mapper = new ObjectMapper();
		if (jsonObj != null) {
			try {
				object = mapper.readValue(jsonData, objClass);
			} catch (IOException e) {
				logger.info("Can't pare jsonObject to Object", e);
			}
		}

		return (T) object;
	}

	public static String convertObjectToJson(Object ar) {
		String json = gson.toJson(ar);

		return json;
	}

}
