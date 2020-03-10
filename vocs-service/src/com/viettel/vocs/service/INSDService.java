package com.viettel.vocs.service;

import java.io.IOException;

import org.springframework.http.codec.multipart.FilePart;

import com.viettel.vocs.bo.ResultArrayBO;
import com.viettel.vocs.bo.ResultObjectBO;
import com.viettel.vocs.bo.ResultValueBO;

import reactor.core.publisher.Mono;

public interface INSDService {

	Mono<ResultArrayBO> getArrayList(String path, String valueInput) throws IOException;

	Mono<ResultArrayBO> postArrayList(String path, String valueInput) throws IOException;

	Mono<ResultObjectBO> getObject(String path, String valueInput) throws IOException;

	Mono<ResultObjectBO> postObject(String path, String valueInput) throws IOException;

	ResultValueBO getValueOverview(String path, String valueInput) throws IOException;

	Mono<ResultObjectBO> putFileCSAR(String path, FilePart valueInput, long contentLenght) throws IOException;

	Mono<ResultValueBO> putFileOther(String path, FilePart valueInput, long contentLenght) throws IOException;

	Mono<ResultObjectBO> putObject(String path, String valueInput) throws IOException;

	Mono<ResultObjectBO> patchObject(String path, Object nsd) throws IOException;

	Mono<ResultObjectBO> deleteObject(String path) throws IOException;

	Mono<ResultValueBO> postObjectResultString(String path, String valueInput) throws IOException;

	Mono<ResultObjectBO> getObjectDownload(String path, String valueInput) throws IOException;

}
