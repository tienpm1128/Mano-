package com.viettel.nfv.vdashboardx.controller;

import com.viettel.nfv.vdashboardx.communication.IErrorManagementController;
import com.viettel.nfv.vdashboardx.exception.ErrHttpCode;
import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.service.IAlarmNotificationService;
import com.viettel.nfv.vdashboardx.service.IAlarmService;
import com.viettel.nfv.vdashboardx.service.IFaultSuscriptionService;
import com.viettel.nfv.vdashboardx.utils.Constants;
import com.viettel.nfv.vdashboardx.utils.DataUtils;
import com.viettel.nfv.vdashboardx.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@PropertySource(value = "classpath:/ws-error-management.properties", ignoreResourceNotFound = true)
@RequestMapping("${vocs-service.common}")
@CrossOrigin(origins = "http://localhost:8084")
public class ErrorManagementController implements IErrorManagementController {

	@Autowired
	private IAlarmService alarmService;

	@Autowired
	private IFaultSuscriptionService faultSubscriptionService;

	@Autowired
	private IAlarmNotificationService alarmNotificationService;

	/**
	 * 1.4.1. List of Alarms
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfAlarms(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
													  .authenticationBearer(authentication)
													  .build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmService.getListOfAlarms(path, requestContent);
	}

	/**
	 * 1.4.2. The detail of alarm
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getAlarmDetail(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmService.getAlarmDetail(path, requestContent);
	}

	/**
	 * 1.4.3. Repair alarm object with alarmId
	 * 
	 * @param request
	 * @param alarm
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> updateAlarm(ServerHttpRequest request, Object alarm, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_409_ALARM);

		RequestContent requestContent = RequestContent.builder()
										.authenticationBearer(authentication)
										.errorCodes(errHttpCode)
										.updatePartObject(alarm)
										.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmService.updateAlarm(path, requestContent);
	}

	/**
	 * 1.4.4. The list of fault subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfFaultSubscription(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return faultSubscriptionService.getListOfFaultSubscription(path, requestContent);
	}

	/**
	 * 1.4.5. Create fault subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createFaultSubscription(ServerHttpRequest request, Object obj, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_FAULT_SUBSCRIPTION);

		String jsonObject = JsonUtils.convertObjectToJson(obj);
		RequestContent requestContent = RequestContent.builder()
														.authenticationBearer(authentication)
														.errorCodes(errHttpCode)
														.bodyData(jsonObject)
														.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return faultSubscriptionService.createFaultSubscription(path, requestContent);
	}

	/**
	 * 1.4.6. The detail of fault subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getFaultSubscriptionDetail(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return faultSubscriptionService.getFaultSubscriptionDetail(path, requestContent);
	}

	/**
	 * 1.4.7. Delete fault subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteFaultSubscription(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return faultSubscriptionService.deleteFaultSubscription(path, requestContent);
	}

	/**
	 * 1.4.8. The list of alarm notification
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfAlarmNofitifcation(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return alarmNotificationService.getListOfAlarmNotifications(path, requestContent);
	}

	/**
	 * 1.4.9. Statistic alarms based on severities
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getAlarmsBySeverities(ServerHttpRequest request, String authentication) throws IOException {
		return getAlarmsBySeverities(request, authentication);
	}

	/**
	 * 1.4.10. Statistic alarms based on event types
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getAlarmsByEventTypes(ServerHttpRequest request, String authentication) throws IOException {
		return getAlarmsBySeverities(request, authentication);
	}

	/**
	 * 1.4.11. Statistic alarms based on fault types
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getAlarmsByFaultTypes(ServerHttpRequest request, String authentication) throws IOException {
		return getAlarmsBySeverities(request, authentication);
	}
}
