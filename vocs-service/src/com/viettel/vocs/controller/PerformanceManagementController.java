package com.viettel.vocs.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.viettel.vocs.service.PerformanceManagement.HypervisorPmNotificationService;
import com.viettel.vocs.service.PerformanceManagement.IHypervisorPmJobService;
import com.viettel.vocs.service.PerformanceManagement.IHypervisorPmNotificationService;
import com.viettel.vocs.service.PerformanceManagement.IHypervisorPmSubscriptionService;
import com.viettel.vocs.service.PerformanceManagement.IHypervisorThresholdService;
import com.viettel.vocs.service.PerformanceManagement.IPmJobService;
import com.viettel.vocs.service.PerformanceManagement.IPmNotificationsService;
import com.viettel.vocs.service.PerformanceManagement.IPmSubscriptionsService;
import com.viettel.vocs.service.PerformanceManagement.IThresholdService;
import com.viettel.vocs.utils.DataUtils;
import com.viettel.vocs.utils.JsonUtils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vocs-service")
@CrossOrigin(origins = "http://localhost:8084")
public class PerformanceManagementController {

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

	/**
	 * 1.5.1. The list of Pm Job
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nspm/v1/pm_jobs" })
	@ResponseBody
	Mono<ResponseEntity<?>> getListOfPmJob(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmJobService.getListOfPmJobs(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.2. Create Pm Job
	 * 
	 * @param request
	 * @param createPmJobRequest
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = { "/nspm/v1/pm_jobs" })
	@ResponseBody
	Mono<ResponseEntity<?>> createPmJob(ServerHttpRequest request, @RequestBody Object createPmJobRequest)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(createPmJobRequest);

		return this.pmJobService.createPmJob(path, jsonObject).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.3. The detail of Pm Job
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nspm/v1/pm_jobs/{pmJobId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getPmJobDetail(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmJobService.getPmJobDetail(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.4. The reports of Pm Job
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nspm/v1/pm_jobs/{pmJobId}/reports/{reportId}" })
	Mono<ResponseEntity<?>> reportPmJo(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmJobService.reportPmJo(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.5. Delete Pm Job
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping(value = { "/nspm/v1/pm_jobs/{pmJobId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> deletePmJob(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmJobService.deletePmJob(path).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.6. The list of Thresholds
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nspm/v1/thresholds" })
	@ResponseBody
	Mono<ResponseEntity<?>> getListOfThresholds(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.thresholdService.getListOfThresholds(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.7. Create threshold
	 * 
	 * @param request
	 * @param createThresholdRequest
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = { "/nspm/v1/thresholds" })
	@ResponseBody
	Mono<ResponseEntity<?>> createThreshold(ServerHttpRequest request, @RequestBody Object createThresholdRequest)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(createThresholdRequest);

		return this.thresholdService.createThreshold(path, jsonObject).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.8. The detail of threshold
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nspm/v1/thresholds/{thresholdId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getThresholdDetail(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.thresholdService.getThresholdDetail(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.9. Delete threshold
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping(value = { "/nspm/v1/thresholds/{thresholdId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> deleteThrehold(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.thresholdService.deleteThreshold(path).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.10. The list of PM Subscriptions
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nspm/v1/subscriptions" })
	@ResponseBody
	Mono<ResponseEntity<?>> getListOfPmSubscriptions(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmSubscriptionService.getListOfPmSubscriptions(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.11. Create PM Subscription
	 * 
	 * @param request
	 * @param pmSubscriptionRequest
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = { "/nspm/v1/subscriptions" })
	@ResponseBody
	Mono<ResponseEntity<?>> createPmSubscription(ServerHttpRequest request, @RequestBody Object pmSubscriptionRequest)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(pmSubscriptionRequest);

		return this.pmSubscriptionService.createPmSubscription(path, jsonObject).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.12. The detail of PM Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nspm/v1/subscriptions/{subscriptionId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getPmSubscriptionDetail(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmSubscriptionService.getPmSubscriptionDetail(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.13. Delete PM Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping(value = { "/nspm/v1/subscriptions/{subscriptionId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> deletePmSubscription(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmSubscriptionService.deletePmSubscription(path).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.14. The list of PM Notifications
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nspm/v1/notifications" })
	@ResponseBody
	Mono<ResponseEntity<?>> getListOfPmNotifications(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmNotificationService.getListOfPmNotifications(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.15. The detail of Pm Job based on Ns Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nspm/v1/pm_jobs/nslcm/{nsInstanceId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getPmJobDetailByNsInstance(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.pmJobService.getPmJobDetailByNsInstanceId(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.16. The detail of Threshold based on Ns Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nspm/v1/thresholds/nslcm/{nsInstanceId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getThresholdDetailByNsInstance(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.thresholdService.getThresholdDetailByNsInstanceId(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.17. The list of Hypervisor PmJob
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/hppm/v1/pm_jobs" })
	@ResponseBody
	Mono<ResponseEntity<?>> getListOfHypervisorPmJob(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmJobService.getListOfHypervisorPmJob(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.18. Create Hypervisor PmJob
	 * 
	 * @param request
	 * @param pmJobRequest
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = { "/hppm/v1/pm_jobs" })
	@ResponseBody
	Mono<ResponseEntity<?>> createHypervisorPmJob(ServerHttpRequest request, @RequestBody Object pmJobRequest)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(pmJobRequest);

		return this.hypervisorPmJobService.createHypervisorPmJob(path, jsonObject).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.19. The detail of Hypervisor PmJob
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/hppm/v1/pm_jobs/{pmJobId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getHypervisorPmJobDetail(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmJobService.getHypervisorPmJobDetail(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.20. Report Hypervisor PmJob
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/hppm/v1/pm_jobs/{pmJobId}/reports/{reportId}" })
	Mono<ResponseEntity<?>> reportHypervisorPmJob(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmJobService.reportHyperPmJob(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.21. Delete Hypervisor PmJob
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping(value = { "/hppm/v1/pm_jobs/{pmJobId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> deleteHypervisorPmJob(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmJobService.deleteHypervisorPmJob(path).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.22. The list of Hypervisor Threshold
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/hppm/v1/thresholds" })
	@ResponseBody
	Mono<ResponseEntity<?>> getListOfHypervisorThreshold(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorThresholdService.getListOfHypervisorThreshold(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.23. Create Hypervisor Threshold
	 * 
	 * @param request
	 * @param thresholdRequest
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = { "/hppm/v1/thresholds" })
	@ResponseBody
	Mono<ResponseEntity<?>> createHypervisorThreshold(ServerHttpRequest request, @RequestBody Object thresholdRequest)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(thresholdRequest);

		return this.hypervisorThresholdService.createHypervisorThreshold(path, jsonObject).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.24. The detail of Hypervisor Threshold
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/hppm/v1/thresholds/{thresholdId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getHypervisorThresholdDetail(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorThresholdService.getHypervisorThresholdDetail(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.25. Delete Hypervisor Threshold
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping(value = { "/hppm/v1/thresholds/{thresholdId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> deleteHypervisorThreshold(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorThresholdService.deleteHypervisorThreshold(path).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.26. The list of Hypervisor Pm Subscriptions
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/hppm/v1/subscriptions" })
	@ResponseBody
	Mono<ResponseEntity<?>> getListOfHypervisorPmSubscription(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmSubscriptionService.getListOfHypervisorPmSubscription(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.27. Create Hypervisor Pm Subscription
	 * 
	 * @param request
	 * @param physicalPmSubscriptionRequest
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = { "/hppm/v1/subscriptions" })
	@ResponseBody
	Mono<ResponseEntity<?>> createHypervisorPmSubscription(ServerHttpRequest request,
			@RequestBody Object physicalPmSubscriptionRequest) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(physicalPmSubscriptionRequest);

		return this.hypervisorPmSubscriptionService.createHypervisorPmSubscription(path, jsonObject).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.28. The detail of Hypervisor Pm Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/hppm/v1/subscriptions/{subscriptionId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getHypervisorPmSubscriptionDetail(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmSubscriptionService.getHypervisorPmSubscriptionDetail(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.29. Delete Hypervisor Pm Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@DeleteMapping(value = { "/hppm/v1/subscriptions/{subscriptionId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> deleteHypervisorPmSubscription(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmSubscriptionService.deleteHypervisorPmSubscription(path).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.30. The list of Hypervisor Pm Notifications
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/hppm/v1/notifications" })
	@ResponseBody
	Mono<ResponseEntity<?>> getListOfHypervisorPmNotification(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmNotificationService.getListOfHypervisorPmNotification(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.31. The detail of PmJob based on HypervisorId
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/hppm/v1/pm_jobs/hypervisor/{hypervisorId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getPmJobDetailByHypervisorId(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorPmJobService.getPmJobDetailByHypervisorId(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.32. The detail of Threshold based on HypervisorId
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/hppm/v1/thresholds/hypervisor/{hypervisorId}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getThresholdDetailByHypervisorId(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorThresholdService.getThresholdDetailByHypervisorId(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.33. The list of Threshold based on VIM
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/vimpm/v1/vimthresholds" })
	@ResponseBody
	Mono<ResponseEntity<?>> getListOfThresholdByVim(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.thresholdService.getListOfThresholdsByVim(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.34. The detail of Threshold based on VIM
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/vimpm/v1/vimthresholds/{id}" })
	@ResponseBody
	Mono<ResponseEntity<?>> getThresholdDetailByVimId(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.hypervisorThresholdService.getThresholdDetailByVimId(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}
}
