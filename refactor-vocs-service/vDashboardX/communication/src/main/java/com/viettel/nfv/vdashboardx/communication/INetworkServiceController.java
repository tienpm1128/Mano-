package com.viettel.nfv.vdashboardx.communication;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface INetworkServiceController {

    /**
     * 1.2.1. The list of NSD
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.ns-descriptions}" })
    @ResponseBody
    default Mono<ResultBO> getDescriptorsApi(ServerHttpRequest request,
                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getDescriptors(request, authentication);
    }

    Mono<ResultBO> getDescriptors(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.2. The detail of NSD
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.ns-descriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> getDetailNSDByIdApi(ServerHttpRequest request,
                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getDetailNSDById(request, authentication);
    }

    Mono<ResultBO> getDetailNSDById(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.3. The list of VNFD based on NSD
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.nsd-id.vnf-descriptions}" })
    @ResponseBody
    default Mono<ResultBO> getVNFDByNSDApi(ServerHttpRequest request,
                                           @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getVNFDByNSD(request, authentication);
    }

    Mono<ResultBO> getVNFDByNSD(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.4. The detail of VNFD
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.vnf-descriptions}" })
    @ResponseBody
    default Mono<ResultBO> getDetailVNFDByIdApi(ServerHttpRequest request,
                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getDetailNSDById(request, authentication);
    }

    Mono<ResultBO> getDetailVNFDById(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.5. Create NSD
     *
     * @param nsd
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${network.service.ns-descriptions}" })
    @ResponseBody
    default Mono<ResultBO> createNSDApi(@RequestBody Object nsd, ServerHttpRequest request,
                                        @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return createNSD(nsd, request, authentication);
    }

    Mono<ResultBO> createNSD(Object nsd, ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.6. Upload content NSD
     *
     * @param request
     * @param file
     * @return
     * @throws IOException
     */
    @PutMapping(value = { "${network.service.ns-descriptions.id.content}" })
    @ResponseBody
    default Mono<ResultBO> uploadFileNSDApi(ServerHttpRequest request, @RequestPart("filePart") Mono<FilePart> file,
                                            @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return uploadFileNSD(request, file, authentication);
    }

    Mono<ResultBO> uploadFileNSD(ServerHttpRequest request, Mono<FilePart> file, String authentication) throws IOException;

    /**
     * 1.2.7. Download content NSD
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.ns-descriptions.id.content}" })
    @ResponseBody
    default Mono<Resource> getFileApi(ServerHttpRequest request,
                                      @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getFile(request, authentication);
    }

    Mono<Resource> getFile(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.8. Update NSD
     *
     * @param request
     * @param nsd
     * @return
     * @throws IOException
     */
    @PatchMapping(value = { "${network.service.ns-descriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> updateNSDApi(ServerHttpRequest request, @RequestBody Object nsd,
                                        @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return updateNSD(request, nsd, authentication);
    }

    Mono<ResultBO> updateNSD(ServerHttpRequest request, Object nsd, String authentication) throws IOException;

    /**
     * 1.2.9. Delete NSD
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${network.service.ns-descriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteNSDApi(ServerHttpRequest request,
                                        @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteNSD(request, authentication);
    }

    Mono<ResultBO> deleteNSD(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.10. The list of NSD Subscriptions
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.nsd-subscriptions}" })
    @ResponseBody
    default Mono<ResultBO> getNSDSubscriptionsApi(ServerHttpRequest request,
                                                  @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getNSDSubscriptions(request, authentication);
    }

    Mono<ResultBO> getNSDSubscriptions(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.11. Delete NSD Subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${network.service.nsd-subscriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteNSDSubscriptionApi(ServerHttpRequest request,
                                                    @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteNSDSubscription(request, authentication);
    }

    Mono<ResultBO> deleteNSDSubscription(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.12. The list of NSD Notifications
     */
    @GetMapping(value = { "${network.service.nsd-notifications}" })
    @ResponseBody
    default Mono<ResultBO> getNSDNotificationsApi(ServerHttpRequest request,
                                                  @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getNSDNotifications(request, authentication);
    }

    Mono<ResultBO> getNSDNotifications(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.13. The list of NS Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.ns-instances}" })
    @ResponseBody
    default Mono<ResultBO> getNSInstanceApi(ServerHttpRequest request,
                                            @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getNSInstance(request, authentication);
    }

    Mono<ResultBO> getNSInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.14. Create NS Instance
     *
     * @param request
     * @param obj
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${network.service.ns-instances}" })
    @ResponseBody
    default Mono<ResultBO> createNSInstanceApi(ServerHttpRequest request, @RequestBody Object obj,
                                               @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return createNSInstance(request, obj, authentication);
    }

    Mono<ResultBO> createNSInstance(ServerHttpRequest request, Object obj, String authentication) throws IOException;

    /**
     * 1.2.15. The detail of NS Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.ns-instances.id}" })
    @ResponseBody
    default Mono<ResultBO> detailNSInstanceApi(ServerHttpRequest request,
                                               @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return detailNSInstance(request, authentication);
    }

    Mono<ResultBO> detailNSInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.16. Initialize VNF Instance
     *
     * @param request
     * @param obj
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${network.service.vnf-instances.id.instantiate}" })
    @ResponseBody
    default Mono<ResultBO> initVNFInstanceApi(ServerHttpRequest request, @RequestBody Object obj,
                                              @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return initVNFInstance(request, obj, authentication);
    }

    Mono<ResultBO> initVNFInstance(ServerHttpRequest request, Object obj, String authentication) throws IOException;

    /**
     * 1.2.17. Scale VNF Instance
     *
     * @param request
     * @param obj
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${network.service.vnf-instances.id.scale}" })
    @ResponseBody
    default Mono<ResultBO> initScaleVNFInstanceApi(ServerHttpRequest request, @RequestBody Object obj,
                                                   @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return initScaleVNFInstance(request, obj, authentication);
    }

    Mono<ResultBO> initScaleVNFInstance(ServerHttpRequest request, Object obj, String authentication) throws IOException;

    /**
     * 1.2.18. Terminate VNF Instance
     *
     * @param request
     * @param obj
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${network.service.vnf-instances.id.terminate}" })
    @ResponseBody
    default Mono<ResultBO> endScaleVNFInstanceApi(ServerHttpRequest request, @RequestBody Object obj,
                                                  @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return endScaleVNFInstance(request, obj, authentication);
    }

    Mono<ResultBO> endScaleVNFInstance(ServerHttpRequest request, Object obj, String authentication) throws IOException;

    /**
     * 1.2.19. Delete VNF Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${network.service.vnf-instances.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteVNFInstanceApi(ServerHttpRequest request,
                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteVNFInstance(request, authentication);
    }

    Mono<ResultBO> deleteVNFInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.20. Delete NS Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${network.service.ns-instances.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteNSInstanceApi(ServerHttpRequest request,
                                               @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteNSInstance(request, authentication);
    }

    Mono<ResultBO> deleteNSInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.21. The list of NS Instance Subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.subscriptions}" })
    @ResponseBody
    default Mono<ResultBO> listNSInstanceSubscriptionApi(ServerHttpRequest request,
                                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return listNSInstanceSubscription(request, authentication);
    }

    Mono<ResultBO> listNSInstanceSubscription(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.22. The list of Ns Instance Subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${network.service.subscriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteNSInstanceSubscriptionApi(ServerHttpRequest request,
                                                           @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteNSInstanceSubscription(request, authentication);
    }

    Mono<ResultBO> deleteNSInstanceSubscription(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.23. Create NS Instance Subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${network.service.subscriptions}" })
    @ResponseBody
    default Mono<ResultBO> createNSInstanceSubscriptionsApi(ServerHttpRequest request, @RequestBody Object obj,
                                                            @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return createNSInstanceSubscriptions(request, obj, authentication);
    }

    Mono<ResultBO> createNSInstanceSubscriptions(ServerHttpRequest request, Object obj, String authentication) throws IOException;

    /**
     * 1.2.24. The list of NS Instance Notification
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.notifications}" })
    @ResponseBody
    default Mono<ResultBO> listNSInstanceNotificationApi(ServerHttpRequest request,
                                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return listNSInstanceNotification(request, authentication);
    }

    Mono<ResultBO> listNSInstanceNotification(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.25. The total of NSD
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.ns-descriptions.total}" })
    @ResponseBody
    default Mono<ResultBO> getTotalOfNsdApi(ServerHttpRequest request,
                                            @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getTotalOfNsd(request, authentication);
    }

    Mono<ResultBO> getTotalOfNsd(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.26. The total of VNFD
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.vnf-descriptions.total}" })
    @ResponseBody
    default Mono<ResultBO> getTotalOfVnfdApi(ServerHttpRequest request,
                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getTotalOfVnfd(request, authentication);
    }

    Mono<ResultBO> getTotalOfVnfd(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.27. The total of NS Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.ns-instances.total}" })
    @ResponseBody
    default Mono<ResultBO> getTotalOfNsInstanceApi(ServerHttpRequest request,
                                                   @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getTotalOfNsInstance(request, authentication);
    }

    Mono<ResultBO> getTotalOfNsInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.28. The list of VNF Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.vnf-instances}" })
    @ResponseBody
    default Mono<ResultBO> lstVNFInstanceApi(ServerHttpRequest request,
                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return lstVNFInstance(request, authentication);
    }

    Mono<ResultBO> lstVNFInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.28. The total of VNF Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.vnf-instances.total}" })
    @ResponseBody
    default Mono<ResultBO> getTotalOfVnfInstancesApi(ServerHttpRequest request,
                                                     @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getTotalOfVnfInstances(request, authentication);
    }

    Mono<ResultBO> getTotalOfVnfInstances(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.2.29. The detail of VNF Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${network.service.vnf-instances.id}" })
    @ResponseBody
    default Mono<ResultBO> detailVNFInstanceApi(ServerHttpRequest request,
                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return detailVNFInstance(request, authentication);
    }

    Mono<ResultBO> detailVNFInstance(ServerHttpRequest request, String authentication) throws IOException;

}
