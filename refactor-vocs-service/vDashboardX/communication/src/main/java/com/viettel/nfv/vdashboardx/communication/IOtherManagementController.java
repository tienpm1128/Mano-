package com.viettel.nfv.vdashboardx.communication;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IOtherManagementController {

    /**
     * 1.6.1. The list of Supporting service
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = "${supporting.service}")
    @ResponseBody
    default Mono<ResultBO> getListOfSupportingServiceApi(ServerHttpRequest request,
                                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfSupportingService(request, authentication);
    }

    Mono<ResultBO> getListOfSupportingService(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.6.2. The list of Mano service
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${mano.service}" })
    @ResponseBody
    default Mono<ResultBO> getListOfManoServicesApi(ServerHttpRequest request,
                                                    @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfManoServices(request, authentication);
    }

    Mono<ResultBO> getListOfManoServices(ServerHttpRequest request, String authentication) throws IOException;

}
