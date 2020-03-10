package com.viettel.nfv.vdashboardx.communication;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IPerformanceManagementController {

    /**
     * 1.5.1. The list of Pm Job
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.pm-jobs}" })
    @ResponseBody
    default Mono<ResultBO> getListOfPmJobApi(ServerHttpRequest request,
                                             @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return getListOfPmJob(request, authentication);
    }

    Mono<com.viettel.nfv.vdashboardx.response.ResultBO> getListOfPmJob(ServerHttpRequest request, String authentication)
            throws IOException;

    /**
     * 1.5.2. Create Pm Job
     *
     * @param request
     * @param createPmJobRequest
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${performance.pm-jobs}" })
    @ResponseBody
    default Mono<ResultBO> createPmJobApi(ServerHttpRequest request, @RequestBody Object createPmJobRequest,
                                          @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return createPmJob(request, createPmJobRequest, authentication);
    }

    Mono<ResultBO> createPmJob(ServerHttpRequest request, Object createPmJobRequest, String authentication) throws IOException;

    /**
     * 1.5.3. The detail of Pm Job
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.pm-jobs.id}" })
    @ResponseBody
    default Mono<ResultBO> getPmJobDetailApi(ServerHttpRequest request,
                                             @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return getPmJobDetail(request, authentication);
    }

    Mono<ResultBO> getPmJobDetail(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.4. The reports of Pm Job
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.pm-jobs.reports.id}" })
    @ResponseBody
    default Mono<ResultBO> reportPmJoApi(ServerHttpRequest request,
                                         @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return reportPmJo(request, authentication);
    }

    Mono<ResultBO> reportPmJo(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.5. Delete Pm Job
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${performance.pm-jobs.id}" })
    @ResponseBody
    default Mono<ResultBO> deletePmJobApi(ServerHttpRequest request,
                                          @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return deletePmJob(request, authentication);
    }

    Mono<ResultBO> deletePmJob(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.6. The list of Thresholds
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.thresholds}" })
    @ResponseBody
    default Mono<ResultBO> getListOfThresholdsApi(ServerHttpRequest request,
                                                  @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return getListOfThresholds(request, authentication);
    }

    Mono<ResultBO> getListOfThresholds(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.7. Create threshold
     *
     * @param request
     * @param createThresholdRequest
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${performance.thresholds}" })
    @ResponseBody
    default Mono<ResultBO> createThresholdApi(ServerHttpRequest request, @RequestBody Object createThresholdRequest,
                                              @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return createThreshold(request, createThresholdRequest, authentication);
    }

    Mono<ResultBO> createThreshold(ServerHttpRequest request, Object createThresholdRequest, String authentication) throws IOException;

    /**
     * 1.5.8. The detail of threshold
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.thresholds.id}" })
    @ResponseBody
    default Mono<ResultBO> getThresholdDetailApi(ServerHttpRequest request,
                                                 @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getThresholdDetail(request, authentication);
    }

    Mono<ResultBO> getThresholdDetail(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.9. Delete threshold
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${performance.thresholds.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteThreholdApi(ServerHttpRequest request, @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteThrehold(request, authentication);
    }

    Mono<ResultBO> deleteThrehold(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.10. The list of PM Subscriptions
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.pm-subscriptions}" })
    @ResponseBody
    default Mono<ResultBO> getListOfPmSubscriptionsApi(ServerHttpRequest request, @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfPmSubscriptions(request, authentication);
    }

    Mono<ResultBO> getListOfPmSubscriptions(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.11. Create PM Subscription
     *
     * @param request
     * @param pmSubscriptionRequest
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${performance.pm-subscriptions}" })
    @ResponseBody
    default Mono<ResultBO> createPmSubscriptionApi(ServerHttpRequest request, @RequestBody Object pmSubscriptionRequest,
                                                   @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return createPmSubscription(request, pmSubscriptionRequest, authentication);
    }

    Mono<ResultBO> createPmSubscription(ServerHttpRequest request, Object pmSubscriptionRequest, String authentication) throws IOException;

    /**
     * 1.5.12. The detail of PM Subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.pm-subscriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> getPmSubscriptionDetailApi(ServerHttpRequest request,
                                                      @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getPmSubscriptionDetail(request, authentication);
    }

    Mono<ResultBO> getPmSubscriptionDetail(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.13. Delete PM Subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${performance.pm-subscriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> deletePmSubscriptionApi(ServerHttpRequest request,
                                                   @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deletePmSubscription(request, authentication);
    }

    Mono<ResultBO> deletePmSubscription(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.14. The list of PM Notifications
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.pm-notifications}" })
    @ResponseBody
    default Mono<ResultBO> getListOfPmNotificationsApi(ServerHttpRequest request,
                                                       @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfPmNotifications(request, authentication);
    }

    Mono<ResultBO> getListOfPmNotifications(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.15. The detail of Pm Job based on Ns Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.pm-jobs.nsInstanceId}" })
    @ResponseBody
    default Mono<ResultBO> getPmJobDetailByNsInstanceApi(ServerHttpRequest request,
                                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getPmJobDetailByNsInstance(request, authentication);
    }

    Mono<ResultBO> getPmJobDetailByNsInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.16. The detail of Threshold based on Ns Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.thresholds.nsInstanceId}" })
    @ResponseBody
    default Mono<ResultBO> getThresholdDetailByNsInstanceApi(ServerHttpRequest request,
                                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getThresholdDetailByNsInstance(request, authentication);
    }

    Mono<ResultBO> getThresholdDetailByNsInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.17. The list of Hypervisor PmJob
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.hppm.pm-jobs}" })
    @ResponseBody
    default Mono<ResultBO> getListOfHypervisorPmJobApi(ServerHttpRequest request,
                                                       @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfHypervisorPmJob(request, authentication);
    }

    Mono<ResultBO> getListOfHypervisorPmJob(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.18. Create Hypervisor PmJob
     *
     * @param request
     * @param pmJobRequest
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${performance.hppm.pm-jobs}" })
    @ResponseBody
    default Mono<ResultBO> createHypervisorPmJobApi(ServerHttpRequest request, @RequestBody Object pmJobRequest,
                                                    @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return createHypervisorPmJob(request, pmJobRequest, authentication);
    }

    Mono<ResultBO> createHypervisorPmJob(ServerHttpRequest request, Object pmJobRequest, String authentication) throws IOException;

    /**
     * 1.5.19. The detail of Hypervisor PmJob
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.hppm.pm-jobs.id}" })
    @ResponseBody
    default Mono<ResultBO> getHypervisorPmJobDetailApi(ServerHttpRequest request,
                                                       @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getHypervisorPmJobDetail(request, authentication);
    }

    Mono<ResultBO> getHypervisorPmJobDetail(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.20. Report Hypervisor PmJob
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.hppm.pm-jobs.reports.reportId}" })
    @ResponseBody
    default Mono<ResultBO> reportHypervisorPmJobApi(ServerHttpRequest request,
                                                    @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return reportHypervisorPmJob(request, authentication);
    }

    Mono<ResultBO> reportHypervisorPmJob(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.21. Delete Hypervisor PmJob
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${performance.hppm.pm-jobs.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteHypervisorPmJobApi(ServerHttpRequest request,
                                                    @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteHypervisorPmJob(request, authentication);
    }

    Mono<ResultBO> deleteHypervisorPmJob(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.22. The list of Hypervisor Threshold
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.hppm.thresholds}" })
    @ResponseBody
    default Mono<ResultBO> getListOfHypervisorThresholdApi(ServerHttpRequest request,
                                                           @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfHypervisorThreshold(request, authentication);
    }

    Mono<ResultBO> getListOfHypervisorThreshold(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.23. Create Hypervisor Threshold
     *
     * @param request
     * @param thresholdRequest
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${performance.hppm.thresholds}" })
    @ResponseBody
    default Mono<ResultBO> createHypervisorThresholdApi(ServerHttpRequest request, @RequestBody Object thresholdRequest,
                                                        @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return createHypervisorThreshold(request, thresholdRequest, authentication);
    }

    Mono<ResultBO> createHypervisorThreshold(ServerHttpRequest request, Object thresholdRequest, String authentication) throws IOException;

    /**
     * 1.5.24. The detail of Hypervisor Threshold
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.hppm.thresholds.id}" })
    @ResponseBody
    default Mono<ResultBO> getHypervisorThresholdDetailApi(ServerHttpRequest request,
                                                           @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getHypervisorThresholdDetail(request, authentication);
    }

    Mono<ResultBO> getHypervisorThresholdDetail(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.25. Delete Hypervisor Threshold
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${performance.hppm.thresholds.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteHypervisorThresholdApi(ServerHttpRequest request,
                                                        @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteHypervisorThreshold(request, authentication);
    }

    Mono<ResultBO> deleteHypervisorThreshold(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.26. The list of Hypervisor Pm Subscriptions
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.hppm.subscriptions}" })
    @ResponseBody
    default Mono<ResultBO> getListOfHypervisorPmSubscriptionApi(ServerHttpRequest request,
                                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfHypervisorPmSubscription(request, authentication);
    }

    Mono<ResultBO> getListOfHypervisorPmSubscription(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.27. Create Hypervisor Pm Subscription
     *
     * @param request
     * @param physicalPmSubscriptionRequest
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${performance.hppm.subscriptions}" })
    @ResponseBody
    default Mono<ResultBO> createHypervisorPmSubscriptionApi(ServerHttpRequest request, @RequestBody Object physicalPmSubscriptionRequest,
                                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return createHypervisorPmSubscription(request, physicalPmSubscriptionRequest, authentication);
    }

    Mono<ResultBO> createHypervisorPmSubscription(ServerHttpRequest request, Object physicalPmSubscriptionRequest, String authentication) throws IOException;

    /**
     * 1.5.28. The detail of Hypervisor Pm Subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.hppm.subscriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> getHypervisorPmSubscriptionDetailApi(ServerHttpRequest request,
                                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getHypervisorPmSubscriptionDetail(request, authentication);
    }

    Mono<ResultBO> getHypervisorPmSubscriptionDetail(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.29. Delete Hypervisor Pm Subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${performance.hppm.subscriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteHypervisorPmSubscriptionApi(ServerHttpRequest request,
                                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteHypervisorPmSubscription(request, authentication);
    }

    Mono<ResultBO> deleteHypervisorPmSubscription(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.30. The list of Hypervisor Pm Notifications
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.hppm.notifications}" })
    @ResponseBody
    default Mono<ResultBO> getListOfHypervisorPmNotificationApi(ServerHttpRequest request,
                                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfHypervisorPmNotification(request, authentication);
    }

    Mono<ResultBO> getListOfHypervisorPmNotification(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.31. The detail of PmJob based on HypervisorId
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.hppm.notifications.id}" })
    @ResponseBody
    default Mono<ResultBO> getPmJobDetailByHypervisorIdApi(ServerHttpRequest request,
                                                           @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getPmJobDetailByHypervisorId(request, authentication);
    }

    Mono<ResultBO> getPmJobDetailByHypervisorId(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.32. The detail of Threshold based on HypervisorId
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.hppm.thresholds.hypervisorId}" })
    @ResponseBody
    default Mono<ResultBO> getThresholdDetailByHypervisorIdApi(ServerHttpRequest request,
                                                               @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getThresholdDetailByHypervisorId(request, authentication);
    }

    Mono<ResultBO> getThresholdDetailByHypervisorId(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.33. The list of Threshold based on VIM
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.vim.thresholds}" })
    @ResponseBody
    default Mono<ResultBO> getListOfThresholdByVimApi(ServerHttpRequest request,
                                                      @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfThresholdByVim(request, authentication);
    }

    Mono<ResultBO> getListOfThresholdByVim(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.5.34. The detail of Threshold based on VIM
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${performance.vim.thresholds.id}" })
    @ResponseBody
    default Mono<ResultBO> getThresholdDetailByVimIdApi(ServerHttpRequest request,
                                                        @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getThresholdDetailByVimId(request, authentication);
    }

    Mono<ResultBO> getThresholdDetailByVimId(ServerHttpRequest request, String authentication) throws IOException;

}
