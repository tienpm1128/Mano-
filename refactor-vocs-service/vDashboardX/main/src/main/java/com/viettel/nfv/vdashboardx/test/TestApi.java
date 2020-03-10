package com.viettel.nfv.vdashboardx.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestApi {

	private static Logger logger = LoggerFactory.getLogger(TestApi.class.getName());

	public static void main(String[] args){
		List<String> lst = new ArrayList();
		lst.add("1");
		lst.add("2");

		if (lst.contains("1")) {
			logger.info("oke");
		} else {
			logger.info("not oke");
		}
	}

	public static void getApi() throws IOException {
		URL url = new URL("http://192.168.100.35:9999/nsd/v1/ns_descriptors");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", "Bearer "
				+ "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGUiOlt7InJvbGVUeXBlIjoiUk9MRV9BRE1JTiIsImF1dGhvcml0eSI6IlJPTEVfQURNSU4ifV0sImVuYWJsZSI6ZmFsc2UsImV4cCI6MTU2MzI3Nzg1NywiaWF0IjoxNTYzMjQ5MDU3fQ.b9TcxMXUVhQ9FKLacZLFWlpfXjTZU-1JporY4ztCq9p412UYivlURkIaH1M6UAuyl7tSyirLOsRAquas8xc1JA");

		int responseCode = conn.getResponseCode();
		logger.info("POST Response Code :  {}", responseCode);
		logger.info("POST Response Message : {}", conn.getResponseMessage());
		if (responseCode == HttpURLConnection.HTTP_CREATED) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
			logger.info("{}",response);
		} else {
			logger.info("POST NOT WORKED");
		}
	}
}
