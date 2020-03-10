package com.viettel.vocs.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Flux;

public class testApi {

	public static void main(String[] args) throws URISyntaxException, IOException, ParseException {
		List<String> lst = new ArrayList();
		lst.add("1");
		lst.add("2");

		if (lst.contains("1")) {
			System.out.println("oke");
		} else {
			System.out.println("not oke");
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
		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + conn.getResponseMessage());
		if (responseCode == HttpURLConnection.HTTP_CREATED) {
			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}

			in.close();
			System.out.println(response.toString());
		} else {
			System.out.println("POST NOT WORKED");
		}
	}

}
