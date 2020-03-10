package com.viettel.vocs.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.viettel.vocs.bo.ErrHttpCode;
import com.viettel.vocs.service.NSDService;
import com.viettel.vocs.utils.Constants;
import com.viettel.vocs.utils.DataUtils;
import com.viettel.vocs.utils.JsonUtils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/vocs-service")
@CrossOrigin(origins = { "http://localhost:8084" })
public class ResourceManagementControllerPart2 {

	@Autowired
	NSDService nsdService;

	ErrHttpCode errHttpCode;

	static final Gson gson = new Gson();

	HashMap<String, String> keyValueheader = null;

	/**
	 * 1.3.30. The list of VM based on Hypervisor
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = {
			"/api/v1/vims/{vimId}/users/{userId}/projects/{projectId}/hypervisors/{hypervisorId}/instances" }, method = {
					RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> lstVMByHypervisor(ServerHttpRequest request) throws IOException {
		String path = request.getPath().toString().replace("/vocs-service", "");
		return this.nsdService.getArrayList(path, null).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.31. Logout VIM instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/op_users/logout" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> logoutVMInstance(ServerHttpRequest request) throws IOException {
		this.errHttpCode = new ErrHttpCode();
		this.errHttpCode.setMes204("Success!");
		Constants.setErrHttpCode(this.errHttpCode);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return this.nsdService.getObject(path, null).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.32. The list of Software
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/sw/v1/software" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> lstSoftware(ServerHttpRequest request) throws IOException {
		String path = request.getPath().toString().replace("/vocs-service", "");
		return this.nsdService.getArrayList(path, null).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.33. Upload Software
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/sw/v1/software/upload" }, method = { RequestMethod.POST })
	@ResponseBody
	public Mono<ResponseEntity<?>> uploadSoftware(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestPart("filePart") FilePart file) throws IOException {
		long k = request.getHeaders().getContentLength();
		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		this.keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(this.keyValueheader);
		this.errHttpCode = new ErrHttpCode();
		this.errHttpCode.setMes201(Constants.MESS_201_UPLOAD_SOFT);
		Constants.setErrHttpCode(this.errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.nsdService.putFileOther(path, file, k).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.34. Update Software
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/sw/v1/software/{id}/update" }, method = { RequestMethod.PUT })
	@ResponseBody
	public Mono<ResponseEntity<?>> updateSoftware(ServerHttpRequest request, @RequestBody Object obj)
			throws IOException {
		long k = request.getHeaders().getContentLength();
		this.errHttpCode = new ErrHttpCode();
		this.errHttpCode.setMes202(Constants.MESS_202_UPDATE);
		Constants.setErrHttpCode(this.errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		return this.nsdService.putObject(path, jsonObject).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.35. Chi tiết Software
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/sw/v1/software/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> detailSoftware(ServerHttpRequest request) throws IOException {
		String path = request.getPath().toString().replace("/vocs-service", "");
		return this.nsdService.getObject(path, null).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.36. Danh sách Image
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/images" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> lstImages(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		this.keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(this.keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return this.nsdService.getArrayList(path, null).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.37. Tạo Image
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param obj
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/images" }, method = { RequestMethod.POST })
	@ResponseBody
	public Mono<ResponseEntity<?>> createImage(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestBody Object obj, @RequestHeader("file-path") String filePath)
			throws IOException {
		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		this.keyValueheader.put("projectId", projectId);
		this.keyValueheader.put("file-path", filePath);
		Constants.setKeyParamHeader(this.keyValueheader);
		this.errHttpCode = new ErrHttpCode();
		this.errHttpCode.setMes201(Constants.MESS_201_IMG);
		this.errHttpCode.setMes409(Constants.MESS_409_IMG_EXIST);
		Constants.setErrHttpCode(this.errHttpCode);

		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		return this.nsdService.postObjectResultString(path, jsonObject).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.38. Xóa Image
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/images/{id}" }, method = { RequestMethod.DELETE })
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteImage(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		this.keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(this.keyValueheader);
		this.errHttpCode = new ErrHttpCode();
		this.errHttpCode.setMes204(Constants.MESS_204_DELETE);
		Constants.setErrHttpCode(this.errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.nsdService.deleteObject(path).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.39. Danh sách Network
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/networks" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> lstNetWork(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		this.keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(this.keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return this.nsdService.getArrayList(path, null).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.40. Danh sách Server Group
	 * 
	 * @param request
	 * @param openStackUserId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/server_groups" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> lstServerGroup(ServerHttpRequest request, @RequestHeader String openStackUserId)
			throws IOException {
		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		Constants.setKeyParamHeader(this.keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return this.nsdService.getArrayList(path, null).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.41. Tạo Server Group
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/server_groups" }, method = { RequestMethod.POST })
	@ResponseBody
	public Mono<ResponseEntity<?>> createServerGroup(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestBody Object obj) throws IOException {
		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		Constants.setKeyParamHeader(this.keyValueheader);
		this.errHttpCode = new ErrHttpCode();
		this.errHttpCode.setMes201(Constants.MESS_201_SERVER_GROUP);
		this.errHttpCode.setMes409(Constants.MESS_409_SERVER_GROUP);
		Constants.setErrHttpCode(this.errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		return this.nsdService.postObjectResultString(path, jsonObject).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.42. Delete Server Group
	 * 
	 * @param request
	 * @param openStackUserId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/server_groups/{id}" }, method = { RequestMethod.DELETE })
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteServerGroup(ServerHttpRequest request, @RequestHeader String openStackUserId)
			throws IOException {
		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		Constants.setKeyParamHeader(this.keyValueheader);

		this.errHttpCode = new ErrHttpCode();
		this.errHttpCode.setMes204(Constants.MESS_204_DELETE);
		Constants.setErrHttpCode(this.errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.nsdService.deleteObject(path).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.43. The list of UserMano
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/user_mgnt/v1/users" })
	@ResponseBody
	public Mono<ResponseEntity<?>> getListOfUserManos(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.nsdService.getArrayList(path, null).map(res -> ResponseEntity.ok(res));
	}

	/**
	 * 1.3.44. Create User Mano
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/user_mgnt/v1/users" }, method = { RequestMethod.POST })
	@ResponseBody
	public Mono<ResponseEntity<?>> createUserMano(ServerHttpRequest request, @RequestBody Object obj)
			throws IOException {
		this.errHttpCode = new ErrHttpCode();
		this.errHttpCode.setMes201(Constants.MESS_201_SUCCESS);
		this.errHttpCode.setMes409(Constants.MESS_409_USER_EXIST);
		Constants.setErrHttpCode(this.errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		return this.nsdService.postObject(path, jsonObject).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.45. The detail of User Mano
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/user_mgnt/v1/users/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> getDetailOfUserMano(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.nsdService.getObject(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.3.46. Update User Mano
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param OpenStackUser
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/user_mgnt/v1/users/{id}" }, method = { RequestMethod.PUT })
	@ResponseBody
	public Mono<ResponseEntity<?>> updateUserMano(ServerHttpRequest request, @RequestBody Object OpenStackUser)
			throws IOException {
		this.errHttpCode = new ErrHttpCode();
		this.errHttpCode.setMes409(Constants.MESS_409_USER_EXIST);
		this.errHttpCode.setMes202(Constants.MESS_202_UPDATE);
		Constants.setErrHttpCode(this.errHttpCode);

		String jsonObject = JsonUtils.convertObjectToJson(OpenStackUser);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.nsdService.putObject(path, jsonObject).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.47. Delete User Mano
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/user_mgnt/v1/users/{id}" }, method = { RequestMethod.DELETE })
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteUserMano(ServerHttpRequest request) throws IOException {
		this.errHttpCode = new ErrHttpCode();
		this.errHttpCode.setMes204(Constants.MESS_204_DELETE);
		Constants.setErrHttpCode(this.errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.nsdService.deleteObject(path).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.48. Storage Quota
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/quotas/storage" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> viewStorageQuota(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		this.keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(this.keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return this.nsdService.getObject(path, null).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.49. The rest of Storage Quota
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/quotas/storage-left" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> viewStorageQuotaLeft(ServerHttpRequest request,
			@RequestHeader String openStackUserId, @RequestHeader String projectId) throws IOException {
		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		this.keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(this.keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return this.nsdService.getObject(path, null).map(result -> ResponseEntity.ok(result));
	}

	/**
	 * 1.3.50. The detail of User Openstack
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param openStackUser
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/op_users/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> getUserOpenstackDetail(ServerHttpRequest request,
			@RequestHeader String openStackUserId, @RequestHeader String projectId, @RequestBody Object openStackUser)
			throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		this.keyValueheader = new HashMap();
		this.keyValueheader.put("openStackUserId", openStackUserId);
		this.keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(this.keyValueheader);

		String jsonObject = JsonUtils.convertObjectToJson(openStackUser);

		return this.nsdService.getObject(path, jsonObject).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.3.51. The total of Vims
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/vims/total" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> getTotalVims(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.nsdService.getObject(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.3.52. The total of Networks
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/networks/total" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> getTotalNetworks(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestHeader String vimId) throws IOException {
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		keyValueheader.put("vimId", vimId);
		Constants.setKeyParamHeader(keyValueheader);

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.nsdService.getObject(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.5.53. The list of Tenants
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/user_mgnt/v1/tenants" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> getTotalTenants(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.nsdService.getArrayList(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.3.54. Create Tenant
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/user_mgnt/v1/tenants" }, method = { RequestMethod.POST })
	@ResponseBody
	public Mono<ResponseEntity<?>> createTenant(ServerHttpRequest request, @RequestBody Object obj) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		String jsonObject = JsonUtils.convertObjectToJson(obj);

		return this.nsdService.getObject(path, jsonObject).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.3.55. The detail of Tenant
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/user_mgnt/v1/tenants/{id}" }, method = { RequestMethod.GET })
	@ResponseBody
	public Mono<ResponseEntity<?>> getDetailOfTenant(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.nsdService.getObject(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.3.56. Delete Tenant
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/user_mgnt/v1/tenants/{id}" }, method = { RequestMethod.DELETE })
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteTenant(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.nsdService.getObject(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}

	/**
	 * 1.3.56. Get list of User Resource Quota
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = { "/user_mgnt/v1/resources/vims/{vimId}" })
	@ResponseBody
	public Mono<ResponseEntity<?>> getListOfUserResourceQuota(ServerHttpRequest request) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());

		return this.nsdService.getArrayList(path, null).map(res -> {
			return ResponseEntity.ok(res);
		});
	}
}
