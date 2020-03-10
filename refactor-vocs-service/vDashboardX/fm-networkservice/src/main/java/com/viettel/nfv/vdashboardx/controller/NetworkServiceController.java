package com.viettel.nfv.vdashboardx.controller;

import com.viettel.nfv.vdashboardx.communication.INetworkServiceController;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;

@RestController
@PropertySource(value = "classpath:/ws-network-service.properties", ignoreResourceNotFound = true)
@RequestMapping("${vocs-service.common}")
@CrossOrigin(origins = "http://localhost:8084")
public class NetworkServiceController implements INetworkServiceController {

	@Autowired
	private INSDService nsdService;

	@Autowired
	private IVNFDService vnfdService;

	@Autowired
	private INSDSubscriptionService nsdSubscriptionService;

	@Autowired
	private INSDNotificationService nsdNotificationService;

	@Autowired
	private INsInstanceService nsInstanceService;

	@Autowired
	private IVnfInstanceService vnfInstanceService;

	@Autowired
	private INsInstanceSubscriptionService nsInstanceSubscriptionService;

	@Autowired
	private INsInstanceNotificationService nsInstanceNotificationService;

	@Value("${spring.path.download}")
	private String pathDownload;

	private static final Logger logger = LoggerFactory.getLogger(NetworkServiceController.class.getName());

	/**
	 * 1.2.1. The list of NSD
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getDescriptors(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdService.getListOfNSDs(path, requestContent);
	}

	/**
	 * 1.2.2. The detail of NSD
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getDetailNSDById(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdService.getNSDDetail(path, requestContent);
	}

	/**
	 * 1.2.3. The list of VNFD based on NSD
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getVNFDByNSD(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return vnfdService.getListOfVNFDs(path, requestContent);
	}

	/**
	 * 1.2.4. The detail of VNFD
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getDetailVNFDById(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return vnfdService.getVNFDDetail(path, requestContent);
	}

	/**
	 * 1.2.5. Create NSD
	 *
	 * @param nsd
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createNSD(Object nsd, ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_NSD_OPENSTK_USER);

		String jsonObject = JsonUtils.convertObjectToJson(nsd);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdService.createNSD(path, requestContent);
	}

	/**
	 * 1.2.6. Upload content NSD
	 *
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> uploadFileNSD(ServerHttpRequest request, Mono<FilePart> file, String authentication)
			throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_UPLOAD_NSD);
		errHttpCode.setMes204(Constants.MESS_204_UPLOAD_NSD);
		errHttpCode.setMes409(Constants.MESS_409_UPLOAD_NSD);


		String path = DataUtils.repalcePath(request.getPath().toString());
		long fileSize = request.getHeaders().getContentLength();

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.filePart(file)
				.build();

		return nsdService.uploadContentNSD(path, requestContent, fileSize);
	}

	/**
	 * 1.2.7. Download content NSD
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<Resource> getFile(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdService.downloadContentNSD(path, requestContent);
	}

	/**
	 * 1.2.8. Update NSD
	 *
	 * @param request
	 * @param nsd
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> updateNSD(ServerHttpRequest request, Object nsd, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_409_DOWNLOAD_NSD);

		String path = DataUtils.repalcePath(request.getPath().toString());

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
//				.bodyData(jsonNsd)
				.updatePartObject(nsd)
				.errorCodes(errHttpCode)
				.build();

		return nsdService.updateNSD(path, requestContent);
	}

	/**
	 * 1.2.9. Delete NSD
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteNSD(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_409_DEL_NSD);
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.deleteNSD(path, requestContent);
	}

	/**
	 * 1.2.10. The list of NSD Subscriptions
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getNSDSubscriptions(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdSubscriptionService.getListOfNSDSubscriptions(path, requestContent);
	}

	/**
	 * 1.2.11. Delete NSD Subscription
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteNSDSubscription(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		logger.info("Path of deleteNSDSubscription: {}", path);
		return nsdSubscriptionService.deleteNSDSubscription(path, requestContent);
	}

	/**
	 * 1.2.12. The list of NSD Notifications
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getNSDNotifications(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdNotificationService.getListOfNsdNotifications(path, requestContent);
	}

	/**
	 * 1.2.13. The list of NS Instance
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getNSInstance(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsInstanceService.getListOfNsInstance(path, requestContent);
	}

	/**
	 * 1.2.14. Create NS Instance
	 *
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createNSInstance(ServerHttpRequest request, Object obj, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_NSD_OPENSTK_USER);
		errHttpCode.setMes409(Constants.MESS_409_NSD_OPENSTK_USER_EXIST);

		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		return nsInstanceService.createNsInstance(path, requestContent);
	}

	/**
	 * 1.2.15. The detail of NS Instance
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> detailNSInstance(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsInstanceService.getNsInstanceDetail(path, requestContent);
	}

	/**
	 * 1.2.16. Initialize VNF Instance
	 *
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> initVNFInstance(ServerHttpRequest request, Object obj, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_VNF_INSTANCE);
		errHttpCode.setMes404(Constants.MESS_404_VNF);
		errHttpCode.setMes409(Constants.MESS_409_INIT_VNF);

		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		return vnfInstanceService.instantiateVnfInstance(path, requestContent);
	}

	/**
	 * 1.2.17. Scale VNF Instance
	 *
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> initScaleVNFInstance(ServerHttpRequest request, Object obj, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_VNF_INSTANCE);
		errHttpCode.setMes404(Constants.MESS_404_VNF);
		errHttpCode.setMes409(Constants.MESS_409_SCALE_VNF);

		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		return vnfInstanceService.scaleVnfInstance(path, requestContent);
	}

	/**
	 * 1.2.18. Terminate VNF Instance
	 *
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> endScaleVNFInstance(ServerHttpRequest request, Object obj, String authentication)
			throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_VNF_INSTANCE);
		errHttpCode.setMes404(Constants.MESS_404_VNF);
		errHttpCode.setMes409(Constants.MESS_409_SCALE_VNF);

		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		return vnfInstanceService.terminateVnfInstance(path, requestContent);
	}

	/**
	 * 1.2.19. Delete VNF Instance
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteVNFInstance(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		errHttpCode.setMes409(Constants.MESS_409_INIT_VNF);

		String path = DataUtils.repalcePath(request.getPath().toString());
		logger.info("Path of deleteVNFInstance:{}", path);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();

		return vnfInstanceService.deleteVnfInstance(path, requestContent);
	}

	/**
	 * 1.2.20. Delete NS Instance
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteNSInstance(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		errHttpCode.setMes409(Constants.MESS_409_INIT_VNF);

		String path = DataUtils.repalcePath(request.getPath().toString());
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();

		return nsInstanceService.deleteNsInstance(path, requestContent);
	}

	/**
	 * 1.2.21. The list of NS Instance Subscription
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> listNSInstanceSubscription(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsInstanceSubscriptionService.getListofNsInstanceSubscription(path, requestContent);
	}

	/**
	 * 1.2.22. The list of Ns Instance Subscription
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteNSInstanceSubscription(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		String path = DataUtils.repalcePath(request.getPath().toString());
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();

		return nsInstanceSubscriptionService.deleteNsInstanceSubscriptionDetail(path, requestContent);
	}

	/**
	 * 1.2.23. Create NS Instance Subscription
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createNSInstanceSubscriptions(ServerHttpRequest request, Object obj, String authentication)
			throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_FAULT_SUBSCRIPTION);

		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		return nsInstanceSubscriptionService.createNsInstanceSubscription(path, requestContent);
	}

	/**
	 * 1.2.24. The list of NS Instance Notification
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> listNSInstanceNotification(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsInstanceNotificationService.getListOfNsInstanceNotification(path, requestContent);
	}

	/**
	 * 1.2.25. The total of NSD
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getTotalOfNsd(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdService.getTotalNsd(path, requestContent);
	}

	/**
	 * 1.2.26. The total of VNFD
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getTotalOfVnfd(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return vnfdService.getTotalVnfd(path, requestContent);
	}

	/**
	 * 1.2.27. The total of NS Instance
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getTotalOfNsInstance(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsInstanceService.getTotalNsInstance(path, requestContent);
	}

	/**
	 * 1.2.28. The list of VNF Instance
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> lstVNFInstance(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return vnfInstanceService.getListOfVnfInstance(path, requestContent);
	}

	/**
	 * 1.2.28. The total of VNF Instance
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getTotalOfVnfInstances(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return vnfInstanceService.getTotalVnfInstance(path, requestContent);
	}

	/**
	 * 1.2.29. The detail of VNF Instance
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> detailVNFInstance(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());

		return vnfInstanceService.getVnfInstanceDetail(path, requestContent);
	}

	public Mono<Resource> downloadTestFile() {
		File fileLocal = new File(pathDownload + "Bugs.xlsx");
		Resource resource = new FileSystemResource(fileLocal);

		return Mono.just(resource);
	}

}
