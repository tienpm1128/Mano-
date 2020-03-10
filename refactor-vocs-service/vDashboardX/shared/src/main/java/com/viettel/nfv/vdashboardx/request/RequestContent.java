package com.viettel.nfv.vdashboardx.request;


import com.viettel.nfv.vdashboardx.exception.ErrHttpCode;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.codec.multipart.FilePart;
import reactor.core.publisher.Mono;

import java.util.Map;

@Data
@Builder
public class RequestContent {

    private Map<String, String> headerParams;

    private String bodyData;

    private ErrHttpCode errorCodes;

    private String authenticationBearer;

    private Object updatePartObject;

    private Mono<FilePart> filePart;

}
