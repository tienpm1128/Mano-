package com.viettel.vocs.utils;

import java.io.FileReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class CrunchifyJSONFileWrite {

	public static JSONArray readArrayFileJson(String filePath, String apiName) {
		JSONParser parser = new JSONParser();
		JSONArray companyList = new JSONArray();

		try {
			// "../test-api.json"
			Object obj = parser.parse(new FileReader(filePath));
			JSONObject jsonObject = (JSONObject) obj;
			companyList = (JSONArray) jsonObject.get(apiName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return companyList;
	}

	public static JSONObject readObjectFileJson(String filePath, String apiName) {
		JSONParser parser = new JSONParser();
		JSONObject companyList;

		try {
			// "../test-api.json"
			Object obj = parser.parse(new FileReader(filePath));
			JSONObject jsonObject = (JSONObject) obj;
			companyList = (JSONObject) jsonObject.get(apiName);

			return companyList;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
