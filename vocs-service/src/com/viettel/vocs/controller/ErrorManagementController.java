package com.viettel.vocs.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vocs.bo.ErrHttpCode;
import com.viettel.vocs.service.ErrorManagement.IAlarmNotificationService;
import com.viettel.vocs.service.ErrorManagement.IAlarmService;
import com.viettel.vocs.service.ErrorManagement.IFaultSuscriptionService;
import com.viettel.vocs.utils.Constants;
import com.viettel.vocs.utils.DataUtils;
import com.viettel.vocs.utils.JsonUtils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vocs-service")
@CrossOrigin(origins = "http://localhost:8084")
public class ErrorManagementController {

	@Autowired
	private IAlarmService alarmService;

	@Autowired
	private IFaultSuscriptionService faultSubscriptionService;

	@Autowired
	private IAlarmNotificationService alarmNotificationService;

	private ErrHttpCode errHttpCode;

	/**
	 * 1.4.1. List of Alarms
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsfm/v1/alarms" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getListOfAlarms(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmService.getListOfAlarms(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.4.2. The detail of alarm
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsfm/v1/alarms/{alarmId}" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getAlarmDetail(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmService.getAlarmDetail(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.4.3. Repair alarm object with alarmId
	 * 
	 * @param request
	 * @param alarm
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsfm/v1/alarms/{alarmId}" }, method = RequestMethod.PATCH)
	@ResponseBody
	public Mono<ResponseEntity<?>> updateAlarm(ServerHttpRequest request, @RequestBody Object alarm)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_409_ALARM);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmService.updateAlarm(path, alarm).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.4.4. The list of fault subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsfm/v1/subscriptions" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getListOfFaultSubscription(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return faultSubscriptionService.getListOfFaultSubscription(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.4.5. Create fault subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsfm/v1/subscriptions" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> createFaultSubscription(ServerHttpRequest request, @RequestBody Object obj)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_FAULT_SUBSCRIPTION);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		String path = DataUtils.repalcePath(request.getPath().toString());

		return faultSubscriptionService.createFaultSubscription(path, jsonObject).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.4.6. The detail of fault subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsfm/v1/subscriptions/{subscriptionId}" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getFaultSubscriptionDetail(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return faultSubscriptionService.getFaultSubscriptionDetail(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.4.7. Delete fault subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsfm/v1/subscriptions/{subscriptionId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteFaultSubscription(ServerHttpRequest request) throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());

		return faultSubscriptionService.deleteFaultSubscription(path).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.4.8. The list of alarm notification
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsfm/v1/notifications" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getListOfAlarmNofitifcation(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmNotificationService.getListOfAlarmNotifications(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.4.9. Statistic alarms based on severities
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsfm/v1/severities" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getAlarmsBySeverities(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmService.getListOfAlarms(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.4.10. Statistic alarms based on event types
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nsfm/v1/event-types" })
	@ResponseBody
	public Mono<ResponseEntity<?>> getAlarmsByEventTypes(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmService.getListOfAlarms(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.4.11. Statistic alarms based on fault types
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nsfm/v1/fault-types" })
	@ResponseBody
	public Mono<ResponseEntity<?>> getAlarmsByFaultTypes(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmService.getListOfAlarms(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}
}
