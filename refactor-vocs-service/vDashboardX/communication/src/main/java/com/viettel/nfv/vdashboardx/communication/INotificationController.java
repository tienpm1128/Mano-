package com.viettel.nfv.vdashboardx.communication;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

public interface INotificationController {

    /**
     * 1.7.1. Send NSD Notifications
     *
     * @param request
     * @param nsMgmtNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.nsd}" })
    @ResponseBody
    default void sendNSDNotificationApi(ServerHttpRequest request, @RequestBody Object nsMgmtNotification) throws IOException {
        sendNSDNotification(request, nsMgmtNotification);
    }

    void sendNSDNotification(ServerHttpRequest request, Object nsMgmtNotification) throws IOException;

    /**
     * 1.7.2. Send NS LCM Notifications
     *
     * @param request
     * @param nsLcmNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.nslcm}" })
    @ResponseBody
    default void sendNsLcmNotificationApi(ServerHttpRequest request, @RequestBody Object nsLcmNotification) throws IOException {
        sendNsLcmNotification(request, nsLcmNotification);
    }

    void sendNsLcmNotification(ServerHttpRequest request, Object nsLcmNotification) throws IOException;

    /**
     * 1.7.3. Send VNF LCM Notifications
     *
     * @param request
     * @param vnfLcmNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.vnflcm}" })
    @ResponseBody
    default void sendVnfLcmNotificationApi(ServerHttpRequest request, @RequestBody Object vnfLcmNotification)
            throws IOException {
        sendVnfLcmNotification(request, vnfLcmNotification);
    }

    void sendVnfLcmNotification(ServerHttpRequest request, Object vnfLcmNotification) throws IOException;

    /**
     * 1.7.4. Send Ns Performance Notifications
     *
     * @param request
     * @param performanceNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.nspm.performance}" })
    @ResponseBody
    default void sendNsPmPerformanceNotificationApi(ServerHttpRequest request, @RequestBody Object performanceNotification) throws IOException {
        sendNsPmPerformanceNotification(request, performanceNotification);
    }

    void sendNsPmPerformanceNotification(ServerHttpRequest request, Object performanceNotification) throws IOException;

    /**
     * 1.7.5. Send Ns Threshold Notifications
     *
     * @param request
     * @param thresholdNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.nspm.threshold}" })
    @ResponseBody
    default void sendNsPmThresholdNotificationApi(ServerHttpRequest request, @RequestBody Object thresholdNotification) throws IOException {
        sendNsPmThresholdNotification(request, thresholdNotification);
    }

    void sendNsPmThresholdNotification(ServerHttpRequest request, Object thresholdNotification) throws IOException;

    /**
     * 1.7.6. Send Vim Notifications
     *
     * @param request
     * @param vimStatusNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.vim}" })
    @ResponseBody
    default void sendVimNotificationApi(ServerHttpRequest request, @RequestBody Object vimStatusNotification)
            throws IOException {
        sendVimNotification(request, vimStatusNotification);
    }

    void sendVimNotification(ServerHttpRequest request, Object vimStatusNotification) throws IOException;

    /**
     * 1.7.7. Send Service Notifications
     *
     * @param request
     * @param nfvoAlarmNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.service}" })
    @ResponseBody
    default void sendServiceNotificationApi(ServerHttpRequest request, @RequestBody Object nfvoAlarmNotification)
            throws IOException {
        sendServiceNotification(request, nfvoAlarmNotification);
    }

    void sendServiceNotification(ServerHttpRequest request, Object nfvoAlarmNotification) throws IOException;

    /**
     * 1.7.8. Send Hypervisor Performance Notifications
     *
     * @param request
     * @param performanceNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.hppm.performance}" })
    @ResponseBody
    default void sendHpPmPerformanceNotificationApi(ServerHttpRequest request, @RequestBody Object performanceNotification)
            throws IOException {
        sendHpPmPerformanceNotification(request, performanceNotification);
    }

    void sendHpPmPerformanceNotification(ServerHttpRequest request, Object performanceNotification) throws IOException;

    /**
     * 1.7.9. Send Hypervisor Threshold Notifications
     *
     * @param request
     * @param thresholdNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.hppm.threshold}" })
    @ResponseBody
    default void sendHpPmThresholdNotificationApi(ServerHttpRequest request, @RequestBody Object thresholdNotification)
            throws IOException {
        sendHpPmThresholdNotification(request, thresholdNotification);
    }

    void sendHpPmThresholdNotification(ServerHttpRequest request, Object thresholdNotification) throws IOException;

    /**
     * 1.7.10. Send Pm Notification
     *
     * @param request
     * @param pmNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.pm}" })
    @ResponseBody
    default void sendPmNotificationApi(ServerHttpRequest request, @RequestBody Object pmNotification) throws IOException {
        sendPmNotification(request, pmNotification);
    }

    void sendPmNotification(ServerHttpRequest request, @RequestBody Object pmNotification) throws IOException;

    /**
     * 1.7.11. Send Fm Notification
     *
     * @param request
     * @param fmNotification
     * @throws IOException
     */
    @PostMapping(value = { "${notification.fm}" })
    @ResponseBody
    default void sendFmNotificationApi(ServerHttpRequest request, @RequestBody Object fmNotification) throws IOException {
        sendPmNotification(request, fmNotification);
    }

    void sendFmNotification(ServerHttpRequest request, @RequestBody Object fmNotification) throws IOException;
}
