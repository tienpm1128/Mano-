package com.viettel.nfv.vdashboardx.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class JsonUtils {

	private static final Logger logger = LoggerFactory.getLogger(JsonUtils.class.getName());

	static final Gson gson = new Gson();

	private JsonUtils() {}

	public static JSONObject convertStringToObject(String output) {
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

	public static JSONObject readFileByKey(JSONObject jsonObj, String key) {
		JSONObject companyList;
		JSONObject jsonObject = jsonObj;
		companyList = (JSONObject) jsonObject.get(key);

		return companyList;
	}

	public static JSONArray convertStringToJsonArray(String output) {
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

	public static <T extends Object> T convertJsonToObject(JSONObject jsonObj, Class<T> objClass) {
		Object object = new Object();
		byte[] jsonData = jsonObj.toString().getBytes();
		ObjectMapper mapper = new ObjectMapper();
		try {
			object = mapper.readValue(jsonData, objClass);
		} catch (IOException e) {
			logger.info("Can't pare jsonObject to Object", e);
		}

		return (T) object;
	}

	public static String convertObjectToJson(Object ar) {
		return gson.toJson(ar);
	}

	/**
	 * get JSONObject based on path to specific field
	 *
	 * @param data
	 * @param path - should have format: "ancestor/parent/child/nephew"
	 * @return
	 */
	public static JSONObject getJsonObjectBasedPath(JSONObject data, String path) {
		// prepare for fields
		List<String> fieldPaths;
		if (!path.contains("/")) {
			fieldPaths = Arrays.asList(path);
		} else {
			fieldPaths = Arrays.asList(path.split("/"));
		}

		// get specific field
		final JSONObject[] currentDataField = { data };
		fieldPaths.stream().forEach(field -> {
			JSONObject tmp = (JSONObject) currentDataField[0].get(field);
			currentDataField[0] = tmp;
		});

		return currentDataField[0];
	}

}
