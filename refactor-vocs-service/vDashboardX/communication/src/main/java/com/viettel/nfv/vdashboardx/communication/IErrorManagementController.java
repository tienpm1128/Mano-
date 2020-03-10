package com.viettel.nfv.vdashboardx.communication;


import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IErrorManagementController {

    /**
     * 1.4.1. List of Alarms
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${error.management.alarms}" })
    @ResponseBody
    default Mono<ResultBO> getListOfAlarmsApi(ServerHttpRequest request, @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfAlarms(request, authentication);
    }

    Mono<ResultBO> getListOfAlarms(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.4.2. The detail of alarm
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${error.management.alarms.id}" })
    @ResponseBody
    default Mono<ResultBO> getAlarmDetailApi(ServerHttpRequest request,
                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getAlarmDetail(request, authentication);
    }

    Mono<ResultBO> getAlarmDetail(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.4.3. Repair alarm object with alarmId
     *
     * @param request
     * @param alarm
     * @return
     * @throws IOException
     */
    @PatchMapping(value = { "${error.management.alarms.id}" })
    @ResponseBody
    default Mono<ResultBO> updateAlarmApi(ServerHttpRequest request, @RequestBody Object alarm,
                                          @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return updateAlarm(request, alarm, authentication);
    }

    Mono<ResultBO> updateAlarm(ServerHttpRequest request, Object alarm, String authentication) throws IOException;

    /**
     * 1.4.4. The list of fault subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${error.management.fault-subscriptions}" })
    @ResponseBody
    default Mono<ResultBO> getListOfFaultSubscriptionApi(ServerHttpRequest request,
                                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfFaultSubscription(request, authentication);
    }

    Mono<ResultBO> getListOfFaultSubscription(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.4.5. Create fault subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${error.management.fault-subscriptions}" })
    @ResponseBody
    default Mono<ResultBO> createFaultSubscriptionApi(ServerHttpRequest request, @RequestBody Object obj,
                                                      @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return createFaultSubscription(request, obj, authentication);
    }

    Mono<ResultBO> createFaultSubscription(ServerHttpRequest request, Object obj, String authentication) throws IOException;

    /**
     * 1.4.6. The detail of fault subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${error.management.fault-subscriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> getFaultSubscriptionDetailApi(ServerHttpRequest request,
                                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getFaultSubscriptionDetail(request, authentication);
    }

    Mono<ResultBO> getFaultSubscriptionDetail(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.4.7. Delete fault subscription
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${error.management.fault-subscriptions.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteFaultSubscriptionApi(ServerHttpRequest request,
                                                      @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteFaultSubscription(request, authentication);
    }

    Mono<ResultBO> deleteFaultSubscription(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.4.8. The list of alarm notification
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${error.management.alarm-notifications}" })
    @ResponseBody
    default Mono<ResultBO> getListOfAlarmNofitifcationApi(ServerHttpRequest request,
                                                          @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfAlarmNofitifcation(request, authentication);
    }

    Mono<ResultBO> getListOfAlarmNofitifcation(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.4.9. Statistic alarms based on severities
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${error.management.alarms.severities}" })
    @ResponseBody
    default Mono<ResultBO> getAlarmsBySeveritiesApi(ServerHttpRequest request,
                                                    @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getAlarmsBySeverities(request, authentication);
    }

    Mono<ResultBO> getAlarmsBySeverities(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.4.10. Statistic alarms based on event types
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${error.management.alarms.event-types}" })
    @ResponseBody
    default Mono<ResultBO> getAlarmsByEventTypesApi(ServerHttpRequest request,
                                                    @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getAlarmsByEventTypes(request, authentication);
    }

    Mono<ResultBO> getAlarmsByEventTypes(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.4.11. Statistic alarms based on fault types
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${error.management.alarms.fault-types}" })
    @ResponseBody
    default Mono<ResultBO> getAlarmsByFaultTypesApi(ServerHttpRequest request,
                                                    @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getAlarmsByFaultTypes(request, authentication);
    }

    Mono<ResultBO> getAlarmsByFaultTypes(ServerHttpRequest request, String authentication) throws IOException;

}
