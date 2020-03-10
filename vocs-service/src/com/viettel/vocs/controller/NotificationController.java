package com.viettel.vocs.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vocs.service.NotificationsService;
import com.viettel.vocs.utils.JsonUtils;

import reactor.core.publisher.ReplayProcessor;

@RestController
@RequestMapping("/vocs-service")
@CrossOrigin(origins = "http://localhost:8084")
public class NotificationController {

	@Autowired
	private NotificationsService notificationService;

	/**
	 * 1.7.1. Send NSD Notifications
	 * 
	 * @param request
	 * @param nsMgmtNotification
	 * @throws IOException
	 */
	@PostMapping(value = { "/nsd/v1/notifications" })
	@ResponseBody
	public void sendNSDNotification(ServerHttpRequest request, @RequestBody Object nsMgmtNotification)
			throws IOException {
		String jsonNsMgmtNotification = JsonUtils.convertObjectToJson(nsMgmtNotification);
		this.notificationService.broadcastNotification(jsonNsMgmtNotification);
	}

	/**
	 * 1.7.2. Send NS LCM Notifications
	 * 
	 * @param request
	 * @param nsLcmNotification
	 * @throws IOException
	 */
	@PostMapping(value = { "/nslcm/v1/notifications" })
	@ResponseBody
	public void sendNsLcmNotification(ServerHttpRequest request, @RequestBody Object nsLcmNotification)
			throws IOException {
		String jsonNsLcmNotification = JsonUtils.convertObjectToJson(nsLcmNotification);
		this.notificationService.broadcastNotification(jsonNsLcmNotification);
	}

	/**
	 * 1.7.3. Send VNF LCM Notifications
	 * 
	 * @param request
	 * @param vnfLcmNotification
	 * @throws IOException
	 */
	@PostMapping(value = { "/vnflcm/v1/notifications" })
	@ResponseBody
	public void sendVnfLcmNotification(ServerHttpRequest request, @RequestBody Object vnfLcmNotification)
			throws IOException {
		String jsonVnfLcmNotification = JsonUtils.convertObjectToJson(vnfLcmNotification);
		this.notificationService.broadcastNotification(jsonVnfLcmNotification);
	}

	/**
	 * 1.7.4. Send PM Notifications
	 * 
	 * @param request
	 * @param pmNotification
	 * @throws IOException
	 */
	@PostMapping(value = { "/nspm/v1/notifications" })
	@ResponseBody
	public void sendPmNotification(ServerHttpRequest request, @RequestBody Object pmNotification) throws IOException {
		String jsonPmNotification = JsonUtils.convertObjectToJson(pmNotification);
		this.notificationService.broadcastNotification(jsonPmNotification);
	}

	/**
	 * 1.7.5. Send Vim Notifications
	 * 
	 * @param request
	 * @param vimStatusNotification
	 * @throws IOException
	 */
	@PostMapping(value = { "/vim/v1/notifications" })
	@ResponseBody
	public void sendVimNotification(ServerHttpRequest request, @RequestBody Object vimStatusNotification)
			throws IOException {
		String jsonVimStatusNotification = JsonUtils.convertObjectToJson(vimStatusNotification);
		this.notificationService.broadcastNotification(jsonVimStatusNotification);
	}

	/**
	 * 1.7.6. Send Service Notifications
	 * 
	 * @param request
	 * @param nfvoAlarmNotification
	 * @throws IOException
	 */
	@PostMapping(value = { "/service/v1/notifications" })
	@ResponseBody
	public void sendServiceNotification(ServerHttpRequest request, @RequestBody Object nfvoAlarmNotification)
			throws IOException {
		String jsonNfvoAlarmNotification = JsonUtils.convertObjectToJson(nfvoAlarmNotification);
		this.notificationService.broadcastNotification(jsonNfvoAlarmNotification);
	}

	/**
	 * 1.7.7. Send Hypervisor Pm Notifications
	 * 
	 * @param request
	 * @param pmNotification
	 * @throws IOException
	 */
	@PostMapping(value = { "/hppm/v1/notifications" })
	@ResponseBody
	public void sendHypervisorPmNotification(ServerHttpRequest request, @RequestBody Object pmNotification)
			throws IOException {
		String jsonNfvoAlarmNotification = JsonUtils.convertObjectToJson(pmNotification);
		this.notificationService.broadcastNotification(jsonNfvoAlarmNotification);
	}

}
