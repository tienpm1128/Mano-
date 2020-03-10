package com.viettel.vocs.controller;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.viettel.vocs.bo.ErrHttpCode;
import com.viettel.vocs.bo.ResultObjectBO;
import com.viettel.vocs.bo.ResultValueBO;
import com.viettel.vocs.bo.ValueOverview;
import com.viettel.vocs.service.NSDService;
import com.viettel.vocs.utils.Constants;
import com.viettel.vocs.utils.DataUtils;
import com.viettel.vocs.utils.JsonUtils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vocs-service")
@CrossOrigin(origins = "http://localhost:8084")
public class NSDController {

	@Autowired
	NSDService nsdService;

	ErrHttpCode errHttpCode;

	static final Gson gson = new Gson();

	/**
	 * 1.2.1. The list of NSD
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/ns_descriptors" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getDescriptors(ServerHttpRequest request) throws IOException {
		String path = request.getPath().toString().replace("/vocs-service", "");

		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});

	}

	/**
	 * 1.2.2. The detail of NSD
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/ns_descriptors/{nsdInfoId}" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getDetailNSDById(ServerHttpRequest request) throws IOException {
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});

	}

	/**
	 * 1.2.3. The list of VNFD based on NSD
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/vnf_descriptors/nsd/{nsdId}" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getVNFDByNSD(ServerHttpRequest request) throws IOException {
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * Overview screen contains APIs:
	 * 1.2.24. The total of NSD
	 * 1.2.25. The total of VNFD
	 * 1.2.26. The total of NS Instance
	 * 1.2.27. The total of VNF instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/total_overview" }, method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Mono<?>> getTotalOverview(ServerHttpRequest request) throws IOException {
		String path = request.getPath().toString().replace("/vocs-service", "");
		ResultObjectBO resultBO = new ResultObjectBO();
		ValueOverview result = new ValueOverview();
		ResultValueBO totalNsd = nsdService.getValueOverview(Constants.GET_TOTAL_NSD, null);
		if (totalNsd.getErrorCode() != Constants.OK) {
			return ResponseEntity.ok(Mono.just(totalNsd));
		}
		ResultValueBO totalvnfd = nsdService.getValueOverview(Constants.GET_TOTAL_VNFD, null);
		if (totalvnfd.getErrorCode() != Constants.OK) {
			return ResponseEntity.ok(Mono.just(totalvnfd));
		}
		ResultValueBO instanceNsd = nsdService.getValueOverview(Constants.GET_INSTANCE_NSD, null);
		if (instanceNsd.getErrorCode() != Constants.OK) {
			return ResponseEntity.ok(Mono.just(instanceNsd));
		}
		ResultValueBO instancevnfd = nsdService.getValueOverview(Constants.GET_INSTANCE_VNFD, null);
		if (instancevnfd.getErrorCode() != Constants.OK) {
			return ResponseEntity.ok(Mono.just(instancevnfd));
		}

		result.setNsDescriptors(Integer.parseInt(totalNsd.getData()));
		result.setNsInstances(Integer.parseInt(instanceNsd.getData()));
		result.setVnfDesciptions(Integer.parseInt(totalvnfd.getData()));
		result.setVnfInstances(Integer.parseInt(instancevnfd.getData()));
		String json = gson.toJson(result);
		JSONObject jsonObject = JsonUtils.ConvertStringToObject(json);
		resultBO.setData(jsonObject);
		resultBO.setError(false);
		resultBO.setErrorCode(Constants.OK);
		return ResponseEntity.ok(Mono.just(resultBO));
	}

	/**
	 * 1.2.4. The detail of VNFD
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/vnf_descriptors/{vnfId}" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getDetailVNFDById(ServerHttpRequest request) throws IOException {
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.5. Create NSD
	 * 
	 * @param nsd
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/ns_descriptors" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> getDetailVNFDById(@RequestBody Object nsd, ServerHttpRequest request)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_NSD_OPENSTK_USER);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(nsd);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.6. Upload content NSD
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/ns_descriptors/{nsdInfoId}/nsd_content" }, method = RequestMethod.PUT)
	@ResponseBody
	public Mono<ResponseEntity<?>> uploadFileNSD(ServerHttpRequest request, @RequestPart("filePart") FilePart file)
			throws IOException {
		long k = request.getHeaders().getContentLength();
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_UPLOAD_NSD);
		errHttpCode.setMes204(Constants.MESS_204_UPLOAD_NSD);
		errHttpCode.setMes409(Constants.MESS_409_UPLOAD_NSD);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.putFileCSAR(path, file, k).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.7. Download content NSD
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/ns_descriptors/{nsdInfoId}/nsd_content" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<Resource> getFile(ServerHttpRequest request) {
		Mono<Resource> result = null;
		try {
			result = nsdService.getObjectDownloadTest();
		} catch (NoSuchFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 1.2.8. Update NSD
	 * 
	 * @param request
	 * @param nsd
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/ns_descriptors/{nsdInfoId}" }, method = RequestMethod.PATCH)
	@ResponseBody
	public Mono<ResponseEntity<?>> updateNSD(ServerHttpRequest request, @RequestBody Object nsd) throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_409_DOWNLOAD_NSD);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.patchObject(path, nsd).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.9. Delete NSD
	 * 
	 * @param request
	 * @param nsd
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/ns_descriptors/{nsdInfoId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteNSD(ServerHttpRequest request) throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_409_DEL_NSD);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.deleteObject(path).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.10. The list of NSD Subscriptions
	 * 
	 * @param request
	 * @param nsd
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/subscriptions" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getNSDSubscriptions(ServerHttpRequest request) throws IOException {

		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.11. Delete NSD Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nsd/v1/subscriptions/{subscriptionId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteNSDSubscription(ServerHttpRequest request) throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.deleteObject(path).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.12. The list of NSD Notifications
	 */
	@RequestMapping(value = { "/nsd/v1/notifications" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getNSDNotifications(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.13. The list of NS Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nslcm/v1/ns_instances" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> getNSInstance(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.14. Create NS Instance
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nslcm/v1/ns_instances" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> createNSInstance(ServerHttpRequest request, @RequestBody Object obj)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_NSD_OPENSTK_USER);
		errHttpCode.setMes409(Constants.MESS_409_NSD_OPENSTK_USER_EXIST);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.15. The detail of NS Instance
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nslcm/v1/ns_instances/{nsInstanceId}" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> detailNSInstance(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.16. Initialize VNF Instance
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/vnflcm/v1/vnf_instances/{vnfInstanceId}/instantiate" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> initVNFInstance(ServerHttpRequest request, @RequestBody Object obj)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_VNF_INSTANCE);
		errHttpCode.setMes404(Constants.MESS_404_VNF);
		errHttpCode.setMes409(Constants.MESS_409_INIT_VNF);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.17. Scale VNF Instance
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/vnflcm/v1/vnf_instances/{vnfInstanceId}/scale" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> initScaleVNFInstance(ServerHttpRequest request, @RequestBody Object obj)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_VNF_INSTANCE);
		errHttpCode.setMes404(Constants.MESS_404_VNF);
		errHttpCode.setMes409(Constants.MESS_409_SCALE_VNF);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.18. Terminate VNF Instance
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = { "/vnflcm/v1/vnf_instances/{vnfInstanceId}/terminate" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> endScaleVNFInstance(ServerHttpRequest request, @RequestBody Object obj)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_VNF_INSTANCE);
		errHttpCode.setMes404(Constants.MESS_404_VNF);
		errHttpCode.setMes409(Constants.MESS_409_SCALE_VNF);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.19. Delete VNF Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = { "/vnflcm/v1/vnf_instances/{vnfInstanceId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteVNFInstance(ServerHttpRequest request) throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		errHttpCode.setMes409(Constants.MESS_409_INIT_VNF);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.deleteObject(path).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.20. Delete NS Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nslcm/v1/ns_instances/{nsInstanceId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteNSInstance(ServerHttpRequest request) throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		errHttpCode.setMes409(Constants.MESS_409_INIT_VNF);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.deleteObject(path).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.21. The list of NS Instance Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nslcm/v1/subscriptions" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> listNSInstanceSubcription(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.22. The list of Ns Instance Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = { "/nslcm/v1/subscriptions/{subscriptionId}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteNSInstanceSubscription(ServerHttpRequest request) throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.deleteObject(path).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.23. Create NS Instance Subscription
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nslcm/v1/subscriptions" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> createNSInstanceSubscriptions(ServerHttpRequest request, @RequestBody Object obj)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_FAULT_SUBSCRIPTION);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.24. The list of NS Instance Notification
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/nslcm/v1/notifications" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> listNSInstanceNotification(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.25. The total of NSD
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nsd/v1/ns_descriptors/total" })
	@ResponseBody
	public Mono<ResponseEntity<?>> getTotalOfNsd(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdService.getObject(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.2.26. The total of VNFD
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nsd/v1/vnf_descriptors/total" })
	@ResponseBody
	public Mono<ResponseEntity<?>> getTotalOfVnfd(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdService.getObject(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.2.27. The total of NS Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nslcm/v1/ns_instances/total" })
	@ResponseBody
	public Mono<ResponseEntity<?>> getTotalOfNsInstance(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdService.getObject(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.2.28. The list of VNF Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/vnflcm/v1/vnf_instances" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> lstVNFInstance(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.2.28. The total of VNF Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/nslcm/v1/vnf_instances/total" })
	@ResponseBody
	public Mono<ResponseEntity<?>> getTotalOfVnfInstances(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdService.getObject(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.2.29. The detail of VNF Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/vnflcm/v1/vnf_instances/{vnfInstanceId}" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> detailVNFInstance(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	private List<HttpMessageConverter<?>> getMessageConverters() {
		List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
		converters.add(new MappingJackson2HttpMessageConverter());
		return converters;
	}

}
