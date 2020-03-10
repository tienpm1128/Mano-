package com.viettel.vocs.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.NoSuchFileException;
import java.util.concurrent.ExecutionException;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.viettel.vocs.bo.ResponseBO;
import com.viettel.vocs.bo.ResultArrayBO;
import com.viettel.vocs.bo.ResultObjectBO;
import com.viettel.vocs.bo.ResultValueBO;
import com.viettel.vocs.controller.NSDController;
import com.viettel.vocs.utils.Constants;
import com.viettel.vocs.utils.ResponseUtils;
import com.viettel.vocs.utils.RestUtils;

import ch.qos.logback.classic.Logger;
import reactor.core.publisher.Mono;

@Service
public class NSDService implements INSDService {
	private static final Logger log = (Logger) LoggerFactory.getLogger(NSDService.class);

	@Autowired
	RestUtils restUtils;
	@Autowired
	ResponseUtils responseUtils;

	@Value("${spring.multipart.max-file-size}")
	private String maxSize;

	@Value("${spring.path.download}")
	private String pathDownload;

	@Override
	public Mono<ResultArrayBO> getArrayList(String path, String valueInput) throws IOException {
		ResponseBO dto = restUtils.getRequest(path, valueInput);
		ResultArrayBO objDto = new ResultArrayBO();
		objDto = responseUtils.resolveArray(dto);

		return Mono.just(objDto);
	}

	@Override
	public Mono<ResultObjectBO> getObject(String path, String valueInput) throws IOException {
		ResultObjectBO objDto = new ResultObjectBO();
		ResponseBO dto = restUtils.getRequest(path, valueInput);
		objDto = responseUtils.resolveObject(dto);

		return Mono.just(objDto);
	}

	@Override
	public ResultValueBO getValueOverview(String path, String valueInput) throws IOException {
		ResultValueBO objDto = new ResultValueBO();
		ResponseBO dto = restUtils.getRequest(path, valueInput);
		objDto = responseUtils.resolveValue(dto);

		return objDto;
	}

	@Override
	public Mono<ResultArrayBO> postArrayList(String path, String valueInput) throws IOException {
		ResponseBO dto = restUtils.postRequest(path, valueInput);
		ResultArrayBO objDto = new ResultArrayBO();
		objDto = responseUtils.resolveArray(dto);

		return Mono.just(objDto);
	}

	@Override
	public Mono<ResultObjectBO> postObject(String path, String valueInput) throws IOException {
		ResultObjectBO objDto = new ResultObjectBO();
		ResponseBO dto = restUtils.postRequest(path, valueInput);
		objDto = responseUtils.resolveObject(dto);

		return Mono.just(objDto);
	}

	@Override
	public Mono<ResultValueBO> postObjectResultString(String path, String valueInput) throws IOException {
		ResultValueBO objDto = new ResultValueBO();
		ResponseBO dto = restUtils.postRequest(path, valueInput);
		objDto = responseUtils.resolveValue(dto);

		return Mono.just(objDto);
	}

	@Override
	public Mono<ResultObjectBO> putFileCSAR(String path, FilePart valueInput, long contentLenght) throws IOException {
		ResultObjectBO objDto = new ResultObjectBO();
		String extension = FilenameUtils.getExtension(valueInput.filename());

		if (!extension.equalsIgnoreCase("CSAR")) {
			objDto.setError(true);
			objDto.setErrorCode(Constants.N_OK);
			objDto.setMessage("The file is not properly formatted!");
			return Mono.just(objDto);
		} else if (contentLenght > Long.parseLong(maxSize)) {
			objDto.setError(true);
			objDto.setErrorCode(Constants.N_OK);
			objDto.setMessage("File size exceeds 10mb!");
			return Mono.just(objDto);
		}

		try {
			ResponseBO dto = restUtils.putRequest(path, valueInput);
			objDto = responseUtils.resolveObject(dto);
		} catch (Exception e) {
			objDto.setError(true);
			objDto.setErrorCode(Constants.N_OK);
			objDto.setMessage("Not found file path!");
		}

		return Mono.just(objDto);
	}

	@Override
	public Mono<ResultValueBO> putFileOther(String path, FilePart valueInput, long contentLenght) throws IOException {
		ResultValueBO objDto = new ResultValueBO();

		try {
			ResponseBO dto = restUtils.postFileRequest(path, valueInput);
			objDto = responseUtils.resolveValue(dto);
		} catch (Exception e) {
			System.out.println("loi " + e.getMessage());
			objDto.setError(true);
			objDto.setErrorCode(Constants.N_OK);
			objDto.setMessage("Not found file path!");
		}

		return Mono.just(objDto);
	}

	@Override
	public Mono<ResultObjectBO> putObject(String path, String valueInput) throws IOException {
		ResultObjectBO objDto = new ResultObjectBO();
		ResponseBO dto = restUtils.putRequestObject(path, valueInput);
		objDto = responseUtils.resolveObject(dto);

		return Mono.just(objDto);
	}

	@Override
	public Mono<ResultObjectBO> patchObject(String path, Object nsd) throws IOException {

		ResultObjectBO objDto = new ResultObjectBO();
		ResponseBO dto = restUtils.PATCHRequest(path, nsd);
		objDto = responseUtils.resolveObject(dto);
		return Mono.just(objDto);
	}

	@Override
	public Mono<ResultObjectBO> deleteObject(String path) throws IOException {
		ResultObjectBO objDto = new ResultObjectBO();
		ResponseBO dto = restUtils.deleteRequest(path);
		objDto = responseUtils.resolveObject(dto);

		return Mono.just(objDto);
	}

	@Override
	public Mono<ResultObjectBO> getObjectDownload(String path, String valueInput) throws IOException {
		ResultObjectBO objDto = new ResultObjectBO();
		ResponseBO dto;

		try {
			dto = restUtils.GETDownloadRequest(path, valueInput);
			objDto = responseUtils.resolveObject(dto);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return Mono.just(objDto);
	}

	public Mono<Resource> getObjectDownloadTest() throws NoSuchFileException {
		File fileLocal = null;
		Resource resource = null;
		fileLocal = new File(pathDownload + "Bugs.xlsx");
		resource = new FileSystemResource(fileLocal);

		return Mono.just(resource);
	}

}
