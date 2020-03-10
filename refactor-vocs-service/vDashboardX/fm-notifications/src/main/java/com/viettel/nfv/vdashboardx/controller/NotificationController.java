package com.viettel.nfv.vdashboardx.controller;

import com.viettel.nfv.vdashboardx.communication.INotificationController;
import com.viettel.nfv.vdashboardx.service.NotificationsService;
import com.viettel.nfv.vdashboardx.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@PropertySource(value = "classpath:/ws-notifications.properties", ignoreResourceNotFound = true)
@RequestMapping("${vocs-service.common}")
@CrossOrigin(origins = "http://localhost:8084")
public class NotificationController implements INotificationController {

	@Autowired
	private NotificationsService notificationService;

	/**
	 * 1.7.1. Send NSD Notifications
	 * 
	 * @param request
	 * @param nsMgmtNotification
	 */
	public void sendNSDNotification(ServerHttpRequest request, Object nsMgmtNotification) {
		String jsonNsMgmtNotification = JsonUtils.convertObjectToJson(nsMgmtNotification);
		this.notificationService.sendNotification(jsonNsMgmtNotification);
	}

	/**
	 * 1.7.2. Send NS LCM Notifications
	 * 
	 * @param request
	 * @param nsLcmNotification
	 */
	public void sendNsLcmNotification(ServerHttpRequest request, Object nsLcmNotification) {
		String jsonNsLcmNotification = JsonUtils.convertObjectToJson(nsLcmNotification);
		this.notificationService.sendNotification(jsonNsLcmNotification);
	}

	/**
	 * 1.7.3. Send VNF LCM Notifications
	 * 
	 * @param request
	 * @param vnfLcmNotification
	 */
	public void sendVnfLcmNotification(ServerHttpRequest request, Object vnfLcmNotification) {
		String jsonVnfLcmNotification = JsonUtils.convertObjectToJson(vnfLcmNotification);
		this.notificationService.sendNotification(jsonVnfLcmNotification);
	}

	/**
	 * 1.7.4. Send Ns Performance Notifications
	 * 
	 * @param request
	 * @param performanceNotification
	 */
	public void sendNsPmPerformanceNotification(ServerHttpRequest request, Object performanceNotification) {
		String jsonPerformanceNotification = JsonUtils.convertObjectToJson(performanceNotification);
		this.notificationService.sendNotification(jsonPerformanceNotification);
	}

	/**
	 * 1.7.5. Send Ns Threshold Notifications
	 *
	 * @param request
	 * @param thresholdNotification
	 */
	public void sendNsPmThresholdNotification(ServerHttpRequest request, Object thresholdNotification) {
		String jsonThresholdNotification = JsonUtils.convertObjectToJson(thresholdNotification);
		this.notificationService.sendNotification(jsonThresholdNotification);
	}

	/**
	 * 1.7.6. Send Vim Notifications
	 * 
	 * @param request
	 * @param vimStatusNotification
	 */
	public void sendVimNotification(ServerHttpRequest request, Object vimStatusNotification) {
		String jsonVimStatusNotification = JsonUtils.convertObjectToJson(vimStatusNotification);
		this.notificationService.sendNotification(jsonVimStatusNotification);
	}

	/**
	 * 1.7.7. Send Service Notifications
	 * 
	 * @param request
	 * @param nfvoAlarmNotification
	 */
	public void sendServiceNotification(ServerHttpRequest request, Object nfvoAlarmNotification) {
		String jsonNfvoAlarmNotification = JsonUtils.convertObjectToJson(nfvoAlarmNotification);
		this.notificationService.sendNotification(jsonNfvoAlarmNotification);
	}

	/**
	 * 1.7.8. Send Hypervisor Pm Notifications
	 * 
	 * @param request
	 * @param pmNotification
	 */
	public void sendHpPmPerformanceNotification(ServerHttpRequest request, Object pmNotification) {
		String jsonNfvoAlarmNotification = JsonUtils.convertObjectToJson(pmNotification);
		this.notificationService.sendNotification(jsonNfvoAlarmNotification);
	}

	/**
	 * 1.7.9. Send Hypervisor Threshold Notifications
	 *
	 * @param request
	 * @param thresholdNotification
	 */
	public void sendHpPmThresholdNotification(ServerHttpRequest request, Object thresholdNotification) {
		String jsonHppmThresholdNotification = JsonUtils.convertObjectToJson(thresholdNotification);
		this.notificationService.sendNotification(jsonHppmThresholdNotification);
	}

	/**
	 * 1.7.10. Send Pm Notification
	 *
	 * @param request
	 * @param pmNotification
	 */
	public void sendPmNotification(ServerHttpRequest request, Object pmNotification) {
		String jsonPmNotification = JsonUtils.convertObjectToJson(pmNotification);
		this.notificationService.sendNotification(jsonPmNotification);
	}

	/**
	 * 1.7.11. Send Fm Notification
	 *
	 * @param request
	 * @param fmNotification
	 */
	public void sendFmNotification(ServerHttpRequest request, Object fmNotification) {
		String jsonFmNotification = JsonUtils.convertObjectToJson(fmNotification);
		this.notificationService.sendNotification(jsonFmNotification);
	}

}
