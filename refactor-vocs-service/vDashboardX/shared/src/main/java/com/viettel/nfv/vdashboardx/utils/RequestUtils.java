package com.viettel.nfv.vdashboardx.utils;

import com.viettel.nfv.vdashboardx.exception.ErrHttpCode;
import com.viettel.nfv.vdashboardx.http_response.*;
import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultBOFactory;
import com.viettel.nfv.vdashboardx.response.ResultType;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

@Component
public class RequestUtils {

    @Autowired
    private WebClient webClient;

    @Value("${spring.path.hostRequest}")
    private String hostRequest;

    @Value("${spring.path.upload}")
    private String pathUploadFile;

    @Value("${spring.path.download}")
    private String pathDownloadFile;

    private static final Logger logger = LoggerFactory.getLogger(RequestUtils.class.getName());

    /**
     * query for all http response
     */
    private static Map<HttpStatus, IHttpResponse> responseHttp = new EnumMap<>(HttpStatus.class);

    static {
        responseHttp.put(HttpStatus.OK, new OkHttp());
        responseHttp.put(HttpStatus.CREATED, new CreatedHttp());
        responseHttp.put(HttpStatus.ACCEPTED, new AcceptHttp());
        responseHttp.put(HttpStatus.NO_CONTENT, new NoContentHttp());
        responseHttp.put(HttpStatus.SEE_OTHER, new SeeOtherHttp());
        responseHttp.put(HttpStatus.UNAUTHORIZED, new UnauthorizedHttp());
        responseHttp.put(HttpStatus.BAD_REQUEST, new BadRequestHttp());
        responseHttp.put(HttpStatus.NOT_FOUND, new NotFoundHttp());
        responseHttp.put(HttpStatus.CONFLICT, new ConflictHttp());
        responseHttp.put(HttpStatus.NOT_ACCEPTABLE, new NoValidLoginVim());
    }

    public Mono<ResultBO> getRequest(String path, ResultType type, RequestContent requestContent) {
        ResultBO resultBO = ResultBOFactory.createResultBO(type);
        Mono<ClientResponse> res = get(path, requestContent);

        return toResponseResult(resultBO, res, requestContent);
    }

    public Mono<ResultBO> postRequest(String path, RequestContent requestContent, ResultType type) {
        ResultBO resultBO = ResultBOFactory.createResultBO(type);
        Mono<ClientResponse> res = post(requestContent, path);

        return toResponseResult(resultBO, res, requestContent);
    }

    public Mono<ResultBO> putRequestObject(String path, RequestContent requestContent, ResultType type) {
        ResultBO resultBO = ResultBOFactory.createResultBO(type);
        Mono<ClientResponse> res = put(path, requestContent);

        return toResponseResult(resultBO, res, requestContent);
    }

    public Mono<ResultBO> deleteRequest(String path, ResultType type, RequestContent requestContent) {
        ResultBO resultBO = ResultBOFactory.createResultBO(type);
        Mono<ClientResponse> res = delete(path, requestContent);

        return toResponseResult(resultBO, res, requestContent);
    }

    public Mono<ResultBO> patchRequest(String path, RequestContent requestContent, ResultType type) {
        ResultBO resultBO = ResultBOFactory.createResultBO(type);
        Mono<ClientResponse> res = patch(path, requestContent);

        return toResponseResult(resultBO, res, requestContent);
    }

    public Mono<Resource> downloadFileRequest(String path, RequestContent requestContent) {
        return this.download(path, requestContent);
    }

    public Mono<ResultBO> uploadFileRequest(String path, RequestContent requestContent, ResultType type, HttpMethod method) {
        ResultBO resultBO = ResultBOFactory.createResultBO(type);
        Mono<ClientResponse> res = upload(path, requestContent, method);

        return toResponseResult(resultBO, res, requestContent);
    }

    private Mono<ClientResponse> post(RequestContent requestContent, String path) {
        // create headers for request
        Consumer<HttpHeaders> consumerHeaders = fillDataToHeaders(requestContent);

        // post request to second server
        return webClient.post()
                        .uri(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .headers(consumerHeaders)
                        .body(BodyInserters.fromPublisher(Mono.just(requestContent.getBodyData()), String.class))
                        .exchange();
    }

    private Mono<ClientResponse> get(String path, RequestContent requestContent) {
        // create headers for request
        Consumer<HttpHeaders> consumerHeaders = fillDataToHeaders(requestContent);

        // send get request to viettel server
        return  webClient.get()
                        .uri(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .headers(consumerHeaders)
                        .exchange();
    }

    private Mono<ClientResponse> put(String path, RequestContent requestContent) {
        // create headers for request
        Consumer<HttpHeaders> consumerHeaders = fillDataToHeaders(requestContent);

        // send put request to second server
        return webClient.put()
                        .uri(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .headers(consumerHeaders)
                        .body(BodyInserters.fromPublisher(Mono.just(requestContent.getBodyData()), String.class))
                        .exchange();
    }

    private Mono<ClientResponse> delete(String path, RequestContent requestContent) {
        // create headers for request
        Consumer<HttpHeaders> consumerHeaders = fillDataToHeaders(requestContent);

        // send get request to other server
        return webClient.delete()
                        .uri(path)
                        .accept(MediaType.APPLICATION_JSON)
                        .headers(consumerHeaders)
                        .exchange();
    }

    private Mono<ClientResponse> patch(String path, RequestContent requestContent) {
        // create headers for request
        Consumer<HttpHeaders> consumerHeaders = fillDataToHeaders(requestContent);

        // send patch request to second server
        return webClient.patch()
                        .uri(path)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .headers(consumerHeaders)
                        .body(BodyInserters.fromValue(requestContent.getUpdatePartObject()))
                        .exchange();
    }

    private Mono<ClientResponse> upload(String path, RequestContent requestContent, HttpMethod method) {
        // create headers for request
        Consumer<HttpHeaders> consumerHeaders = fillDataToHeaders(requestContent);

        // prepare multi part to upload file
        return requestContent.getFilePart().filter(part -> part instanceof FilePart)
                .ofType(FilePart.class)
                .flatMap(part -> {
                    // create folder if not exist
                    File clientFile = null;
                    boolean hasCreatedFolder = FileUtils.createFolder(this.pathUploadFile);
                    if (!hasCreatedFolder) {
                        return Mono.error(new Exception("Upload folder path is not defined!"));
                    }

                    try {
                        clientFile = FileUtils.createUploadFile(part, this.pathUploadFile);
                    } catch (IOException e) {
                        return Mono.error(e);
                    }

                    part.transferTo(clientFile);

                    // prepare multi part for uploading to server
                    Resource resource = new FileSystemResource(clientFile);
                    MultiValueMap<String, Object> formData = new LinkedMultiValueMap<>();
                    formData.add("filePart", resource);

                    // send upload request
                    return webClient.method(method)
                                    .uri(path)
                                    .contentType(MediaType.MULTIPART_FORM_DATA)
                                    .headers(consumerHeaders)
                                    .body(BodyInserters.fromMultipartData(formData))
                                    .exchange();
        });
    }

    private Mono<Resource> download(String path, RequestContent requestContent) {
        // create headers for request
        Consumer<HttpHeaders> consumerHeaders = fillDataToHeaders(requestContent);

        Mono<ClientResponse> res = webClient.get()
                                            .uri(path)
                                            .accept(MediaType.APPLICATION_OCTET_STREAM)
                                            .headers(consumerHeaders)
                                            .exchange();

        return this.toResourceFile(res);
    }

    private Mono<ResultBO> toResponseResult(ResultBO resultBO, Mono<ClientResponse> res, RequestContent requestContent)  {
        return res.flatMap(clientResponse -> {
            HttpStatus status = clientResponse.statusCode();
            Mono<String> respBackendVt = clientResponse.bodyToMono(String.class).switchIfEmpty(Mono.just(""));

            return respBackendVt.flatMap(item -> {
                Object trulyResponseJson = null;
                JSONObject jsonError = null;

                if (isStatusAlright(status)) {
                    trulyResponseJson = resultBO.toJsonObject(item);
                    logger.info("=================== Response Json string is: ==================\n".concat(trulyResponseJson.toString()));
                } else {
                    jsonError = JsonUtils.convertStringToObject(item);
                }

                ErrHttpCode errMessCode = requestContent.getErrorCodes();
                if (Objects.isNull(errMessCode)) {
                    errMessCode = new ErrHttpCode();
                }

                HttpResponseParam httpParam = new HttpResponseParam(trulyResponseJson, jsonError, errMessCode);
                IHttpResponse httpResponse = responseHttp.get(status);

                // delete upload file if exists
                try {
                    FileUtils.deleteUploadFile();
                } catch (IOException e) {
                    logger.error("Error when deleting file: ", e);
                }

                if (httpResponse != null) {
                    return Mono.just(httpResponse.toResultData(resultBO, httpParam));
                }

                // if not found http response, return default value
                logger.info("Status for False: {}", status);
                resultBO.setError(true);
                resultBO.setErrorCode(Constants.N_OK);
                resultBO.setMessage("False!");

                return Mono.just(resultBO);
            });
        });
    }

    private Mono<Resource> toResourceFile(Mono<ClientResponse> res) {
        return res.flatMap(clientResponse -> clientResponse.bodyToMono(Resource.class));
    }

    private Consumer<HttpHeaders> fillDataToHeaders(RequestContent requestContent) {
        String basicAuth = requestContent.getAuthenticationBearer();
        MultiValueMap<String, String> multiValueMap = getValueForHeaderParameters(requestContent);

        return httpHeaders -> {
            httpHeaders.add(HttpHeaders.AUTHORIZATION, basicAuth);
            httpHeaders.addAll(multiValueMap);
        };
    }

    private MultiValueMap<String, String> getValueForHeaderParameters(RequestContent requestContent) {
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        Map<String, String> keyValueHeader = requestContent.getHeaderParams();
        if (Objects.isNull(keyValueHeader)) {
            keyValueHeader = new HashMap<>();
        }

        for (Map.Entry<String, String> item : keyValueHeader.entrySet()) {
            multiValueMap.put(item.getKey(), Collections.singletonList(item.getValue()));
        }

        return multiValueMap;
    }

    private boolean isStatusAlright(HttpStatus status) {
        return status.is2xxSuccessful();
    }

}
