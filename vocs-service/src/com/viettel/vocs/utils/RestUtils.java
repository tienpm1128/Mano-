package com.viettel.vocs.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.HttpResponseBodyPart;
import org.asynchttpclient.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.viettel.vocs.bo.ResponseBO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestUtils {

	@Value("${spring.path.hostRequest}")
	private String host;

	@Value("${spring.path.upload}")
	private String pathUpload;
	HashMap<String, String> keyValueHeader = new HashMap<String, String>();

	public ResponseBO getRequest(String path, String valueInput) throws IOException {
		ResponseBO dto = new ResponseBO();
		URL url = new URL(host + path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		keyValueHeader = Constants.getKeyParamHeader();
		for (Map.Entry<String, String> me : keyValueHeader.entrySet()) {
			conn.setRequestProperty(me.getKey().toString(), me.getValue().toString());
		}
		if (StringUtils.isNotEmpty(Constants.SESSION_TOKEN)) {
			conn.setRequestProperty("Authorization", Constants.SESSION_TOKEN);
		}
		if (StringUtils.isNotEmpty(valueInput)) {
			OutputStream os = conn.getOutputStream();
			os.write(valueInput.getBytes());
			os.flush();
			os.close();
		}
		int responseCode = conn.getResponseCode();
		dto.setConnect(conn);
		dto.setErrorCode(responseCode);
		return dto;

	}

	public ResponseBO GETDownloadRequest(String path, String valueInput)
			throws IOException, InterruptedException, ExecutionException {
		ResponseBO dto = new ResponseBO();
		String pathLocal = pathUpload;
		File fileLocal = new File(path);
		if (!fileLocal.exists())
			fileLocal.mkdirs(); // or file.mkdir()

		FileOutputStream stream = new FileOutputStream(pathUpload + "Bugs.xlsx");
		AsyncHttpClient client = Dsl.asyncHttpClient();
		BoundRequestBuilder builder = client.prepareGet(host + path);
		keyValueHeader = Constants.getKeyParamHeader();
		for (Map.Entry<String, String> me : keyValueHeader.entrySet()) {
			builder.addHeader(me.getKey().toString(), me.getValue().toString());
		}
		if (StringUtils.isNotEmpty(Constants.SESSION_TOKEN)) {
			builder.addHeader("Authorization", Constants.SESSION_TOKEN);
		}
		builder.execute(new AsyncCompletionHandler<FileOutputStream>() {

			@Override
			public State onBodyPartReceived(HttpResponseBodyPart bodyPart) throws Exception {
				stream.getChannel().write(bodyPart.getBodyByteBuffer());
				return State.CONTINUE;
			}

			@Override
			public FileOutputStream onCompleted(Response response) throws Exception {
				return stream;
			}
		}).get();

		stream.getChannel().close();
		client.close();
		return dto;

	}

	public ResponseBO postRequest(String path, String valueInput) throws IOException {
		ResponseBO dto = new ResponseBO();
		URL url = new URL(host + path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		keyValueHeader = Constants.getKeyParamHeader();
		for (Map.Entry<String, String> me : keyValueHeader.entrySet()) {
			conn.setRequestProperty(me.getKey().toString(), me.getValue().toString());
		}
		if (StringUtils.isNotEmpty(Constants.SESSION_TOKEN)) {
			conn.setRequestProperty("Authorization", Constants.SESSION_TOKEN);
		}

		if (StringUtils.isNotEmpty(valueInput)) {
			OutputStream os = conn.getOutputStream();

			os.write(valueInput.getBytes());
			os.flush();
			os.close();
		}
		int responseCode = conn.getResponseCode();
		dto.setConnect(conn);
		dto.setErrorCode(responseCode);
		return dto;
	}

	public ResponseBO putRequest(String path, FilePart file) throws IOException {
		ResponseBO dto = new ResponseBO();

		String pathLocal = pathUpload;
		File fileLocal = new File(path);
		if (!fileLocal.exists())
			fileLocal.mkdirs(); // or file.mkdir()

		fileLocal = new File(pathLocal + "/" + file.filename());
		fileLocal.createNewFile();

		file.transferTo(fileLocal);
		Resource resource = new FileSystemResource(fileLocal);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("filePart", resource);
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new FormHttpMessageConverter());
		messageConverters.add(new MappingJackson2HttpMessageConverter());
		restTemplate.setMessageConverters(messageConverters);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		keyValueHeader = Constants.getKeyParamHeader();
		for (Map.Entry<String, String> me : keyValueHeader.entrySet()) {
			headers.set(me.getKey().toString(), me.getValue().toString());
		}
		headers.set("Authorization", Constants.SESSION_TOKEN);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		try {
			ResponseEntity<String> response = restTemplate.exchange(host + path, HttpMethod.PUT, requestEntity,
					String.class);
			dto.setErrorCode(response.getStatusCodeValue());
			dto.setBody(response.getBody());
		} catch (HttpStatusCodeException exception) {
			dto.setErrorCode(exception.getStatusCode().value());
		}

		// xoa tat ca file trong thu muc upload
		FileUtils.cleanDirectory(new File(pathUpload));
		return dto;
	}

	public ResponseBO postFileRequest(String path, FilePart file) throws IOException {
		ResponseBO dto = new ResponseBO();
		String pathLocal = pathUpload;
		File fileLocal = new File(path);
		if (!fileLocal.exists())
			fileLocal.mkdirs(); // or file.mkdir()

		fileLocal = new File(pathLocal + "/" + file.filename());
		fileLocal.createNewFile();

		file.transferTo(fileLocal);
		Resource resource = new FileSystemResource(fileLocal);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		keyValueHeader = Constants.getKeyParamHeader();
		for (Map.Entry<String, String> me : keyValueHeader.entrySet()) {
			headers.set(me.getKey().toString(), me.getValue().toString());
		}
		headers.set("Authorization", Constants.SESSION_TOKEN);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("filePart", resource);

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		String serverUrl = host + path;

		RestTemplate restTemplate = new RestTemplate();

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(serverUrl, requestEntity, String.class);
			dto.setErrorCode(response.getStatusCodeValue());
			dto.setBody(response.getBody());
		} catch (HttpStatusCodeException exception) {
			dto.setErrorCode(exception.getStatusCode().value());
		}
		// xoa tat ca file trong thu muc upload
		FileUtils.cleanDirectory(new File(pathUpload));
		return dto;
	}

	public ResponseBO putRequestObject(String path, String valueInput) throws IOException {
		ResponseBO dto = new ResponseBO();
		URL url = new URL(host + path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("PUT");
		conn.setRequestProperty("Content-Type", "application/json");
		keyValueHeader = Constants.getKeyParamHeader();
		for (Map.Entry<String, String> me : keyValueHeader.entrySet()) {
			conn.setRequestProperty(me.getKey().toString(), me.getValue().toString());
		}
		if (StringUtils.isNotEmpty(Constants.SESSION_TOKEN)) {
			conn.setRequestProperty("Authorization", Constants.SESSION_TOKEN);
		}

		if (StringUtils.isNotEmpty(valueInput)) {
			OutputStream os = conn.getOutputStream();

			os.write(valueInput.getBytes());
			os.flush();
			os.close();
		}
		int responseCode = conn.getResponseCode();
		dto.setConnect(conn);
		dto.setErrorCode(responseCode);
		return dto;
	}

	public ResponseBO deleteRequest(String path) throws IOException {
		ResponseBO dto = new ResponseBO();
		URL url = new URL(host + path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("DELETE");
		conn.setRequestProperty("Content-Type", "application/json");
		keyValueHeader = Constants.getKeyParamHeader();
		for (Map.Entry<String, String> me : keyValueHeader.entrySet()) {
			conn.setRequestProperty(me.getKey().toString(), me.getValue().toString());
		}
		if (StringUtils.isNotEmpty(Constants.SESSION_TOKEN)) {
			conn.setRequestProperty("Authorization", Constants.SESSION_TOKEN);
		}

		int responseCode = conn.getResponseCode();
		dto.setConnect(conn);
		dto.setErrorCode(responseCode);
		return dto;
	}

	public ResponseBO PATCHRequest(String path, Object nsd) throws IOException {
		ResponseBO dto = new ResponseBO();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", Constants.SESSION_TOKEN);
		keyValueHeader = Constants.getKeyParamHeader();
		for (Map.Entry<String, String> me : keyValueHeader.entrySet()) {
			headers.set(me.getKey().toString(), me.getValue().toString());
		}
		HttpEntity<?> httpEntity = new HttpEntity<Object>(nsd, headers);
		URL url = new URL(host + path);
		RestTemplate restTemplate = new RestTemplate();
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		restTemplate.setRequestFactory(requestFactory);
		try {
			ResponseEntity<String> response = restTemplate.exchange(host + path, HttpMethod.PATCH, httpEntity,
					String.class);
			dto.setErrorCode(response.getStatusCodeValue());
			dto.setBody(response.getBody());
		} catch (HttpStatusCodeException exception) {
			dto.setErrorCode(exception.getStatusCode().value());
			dto.setBody("");
		}
		return dto;
	}

	public String readFile(File file) throws IOException {
		return new String(Files.readAllBytes(file.toPath()), "UTF-8");
	}
}
