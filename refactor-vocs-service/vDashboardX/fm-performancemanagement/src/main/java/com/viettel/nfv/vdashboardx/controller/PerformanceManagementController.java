package com.viettel.nfv.vdashboardx.controller;

import com.viettel.nfv.vdashboardx.communication.IPerformanceManagementController;
import com.viettel.nfv.vdashboardx.exception.ErrHttpCode;
import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.service.*;
import com.viettel.nfv.vdashboardx.utils.Constants;
import com.viettel.nfv.vdashboardx.utils.DataUtils;
import com.viettel.nfv.vdashboardx.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@PropertySource(value = "classpath:/ws-performance.properties", ignoreResourceNotFound = true)
@RequestMapping("${vocs-service.common}")
@CrossOrigin(origins = "http://localhost:8084")
public class PerformanceManagementController implements IPerformanceManagementController {

	@Autowired
	private IPmJobService pmJobService;

	@Autowired
	private IThresholdService thresholdService;

	@Autowired
	private IPmSubscriptionsService pmSubscriptionService;

	@Autowired
	private IPmNotificationsService pmNotificationService;

	@Autowired
	private IHypervisorPmJobService hypervisorPmJobService;

	@Autowired
	private IHypervisorPmNotificationService hypervisorPmNotificationService;

	@Autowired
	private IHypervisorThresholdService hypervisorThresholdService;

	@Autowired
	private IHypervisorPmSubscriptionService hypervisorPmSubscriptionService;

	private static final Logger logger = LoggerFactory.getLogger(PerformanceManagementController.class.getName());

	/**
	 * 1.5.1. The list of Pm Job
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfPmJob(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmJobService.getListOfPmJobs(path, requestContent);
	}

	/**
	 * 1.5.2. Create Pm Job
	 * 
	 * @param request
	 * @param createPmJobRequest
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createPmJob(ServerHttpRequest request, Object createPmJobRequest, String authentication)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(createPmJobRequest);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.build();

		return this.pmJobService.createPmJob(path, requestContent);
	}

	/**
	 * 1.5.3. The detail of Pm Job
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getPmJobDetail(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmJobService.getPmJobDetail(path, requestContent);
	}

	/**
	 * 1.5.4. The reports of Pm Job
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> reportPmJo(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmJobService.reportPmJo(path, requestContent);
	}

	/**
	 * 1.5.5. Delete Pm Job
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deletePmJob(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());
		logger.info("Path of delete pmjob: {}", path);

		return this.pmJobService.deletePmJob(path, requestContent);
	}

	/**
	 * 1.5.6. The list of Thresholds
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfThresholds(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.thresholdService.getListOfThresholds(path, requestContent);
	}

	/**
	 * 1.5.7. Create threshold
	 * 
	 * @param request
	 * @param createThresholdRequest
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createThreshold(ServerHttpRequest request, Object createThresholdRequest, String authentication)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(createThresholdRequest);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.build();

		return this.thresholdService.createThreshold(path, requestContent);
	}

	/**
	 * 1.5.8. The detail of threshold
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getThresholdDetail(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.thresholdService.getThresholdDetail(path, requestContent);
	}

	/**
	 * 1.5.9. Delete threshold
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteThrehold(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());
		logger.info("Path of deleteThrehold:{}", path);

		return this.thresholdService.deleteThreshold(path, requestContent);
	}

	/**
	 * 1.5.10. The list of PM Subscriptions
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfPmSubscriptions(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmSubscriptionService.getListOfPmSubscriptions(path, requestContent);
	}

	/**
	 * 1.5.11. Create PM Subscription
	 * 
	 * @param request
	 * @param pmSubscriptionRequest
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createPmSubscription(ServerHttpRequest request, Object pmSubscriptionRequest, String authentication)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(pmSubscriptionRequest);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.build();

		return this.pmSubscriptionService.createPmSubscription(path, requestContent);
	}

	/**
	 * 1.5.12. The detail of PM Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getPmSubscriptionDetail(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmSubscriptionService.getPmSubscriptionDetail(path, requestContent);
	}

	/**
	 * 1.5.13. Delete PM Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deletePmSubscription(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());
		logger.info("Path of deletePmSubscription: {}", path);

		return this.pmSubscriptionService.deletePmSubscription(path, requestContent);
	}

	/**
	 * 1.5.14. The list of PM Notifications
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfPmNotifications(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmNotificationService.getListOfPmNotifications(path, requestContent);
	}

	/**
	 * 1.5.15. The detail of Pm Job based on Ns Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getPmJobDetailByNsInstance(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmJobService.getPmJobDetailByNsInstanceId(path, requestContent);
	}

	/**
	 * 1.5.16. The detail of Threshold based on Ns Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getThresholdDetailByNsInstance(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.thresholdService.getThresholdDetailByNsInstanceId(path, requestContent);
	}

	/**
	 * 1.5.17. The list of Hypervisor PmJob
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfHypervisorPmJob(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmJobService.getListOfHypervisorPmJob(path, requestContent);
	}

	/**
	 * 1.5.18. Create Hypervisor PmJob
	 * 
	 * @param request
	 * @param pmJobRequest
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createHypervisorPmJob(ServerHttpRequest request, Object pmJobRequest, String authentication)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(pmJobRequest);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.build();

		return this.hypervisorPmJobService.createHypervisorPmJob(path, requestContent);
	}

	/**
	 * 1.5.19. The detail of Hypervisor PmJob
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getHypervisorPmJobDetail(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmJobService.getHypervisorPmJobDetail(path, requestContent);
	}

	/**
	 * 1.5.20. Report Hypervisor PmJob
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> reportHypervisorPmJob(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmJobService.reportHyperPmJob(path, requestContent);
	}

	/**
	 * 1.5.21. Delete Hypervisor PmJob
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteHypervisorPmJob(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmJobService.deleteHypervisorPmJob(path, requestContent);
	}

	/**
	 * 1.5.22. The list of Hypervisor Threshold
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfHypervisorThreshold(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorThresholdService.getListOfHypervisorThreshold(path, requestContent);
	}

	/**
	 * 1.5.23. Create Hypervisor Threshold
	 * 
	 * @param request
	 * @param thresholdRequest
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createHypervisorThreshold(ServerHttpRequest request, Object thresholdRequest, String authentication)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(thresholdRequest);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.build();

		return this.hypervisorThresholdService.createHypervisorThreshold(path, requestContent);
	}

	/**
	 * 1.5.24. The detail of Hypervisor Threshold
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getHypervisorThresholdDetail(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorThresholdService.getHypervisorThresholdDetail(path, requestContent);
	}

	/**
	 * 1.5.25. Delete Hypervisor Threshold
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteHypervisorThreshold(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorThresholdService.deleteHypervisorThreshold(path, requestContent);
	}

	/**
	 * 1.5.26. The list of Hypervisor Pm Subscriptions
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfHypervisorPmSubscription(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmSubscriptionService.getListOfHypervisorPmSubscription(path, requestContent);
	}

	/**
	 * 1.5.27. Create Hypervisor Pm Subscription
	 * 
	 * @param request
	 * @param physicalPmSubscriptionRequest
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createHypervisorPmSubscription(ServerHttpRequest request, Object physicalPmSubscriptionRequest, String authentication) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(physicalPmSubscriptionRequest);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.build();

		return this.hypervisorPmSubscriptionService.createHypervisorPmSubscription(path, requestContent);
	}

	/**
	 * 1.5.28. The detail of Hypervisor Pm Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getHypervisorPmSubscriptionDetail(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmSubscriptionService.getHypervisorPmSubscriptionDetail(path, requestContent);
	}

	/**
	 * 1.5.29. Delete Hypervisor Pm Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteHypervisorPmSubscription(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmSubscriptionService.deleteHypervisorPmSubscription(path, requestContent);
	}

	/**
	 * 1.5.30. The list of Hypervisor Pm Notifications
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfHypervisorPmNotification(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmNotificationService.getListOfHypervisorPmNotification(path, requestContent);
	}

	/**
	 * 1.5.31. The detail of PmJob based on HypervisorId
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getPmJobDetailByHypervisorId(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmJobService.getPmJobDetailByHypervisorId(path, requestContent);
	}

	/**
	 * 1.5.32. The detail of Threshold based on HypervisorId
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getThresholdDetailByHypervisorId(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorThresholdService.getThresholdDetailByHypervisorId(path, requestContent);
	}

	/**
	 * 1.5.33. The list of Threshold based on VIM
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfThresholdByVim(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.thresholdService.getListOfThresholdsByVim(path, requestContent);
	}

	/**
	 * 1.5.34. The detail of Threshold based on VIM
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getThresholdDetailByVimId(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorThresholdService.getThresholdDetailByVimId(path, requestContent);
	}
}
