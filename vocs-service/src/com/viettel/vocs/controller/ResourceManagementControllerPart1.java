package com.viettel.vocs.controller;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
@CrossOrigin(origins = "http://localhost:8084")
public class ResourceManagementControllerPart1 {
	@Autowired
	NSDService nsdService;

	ErrHttpCode errHttpCode;

	static final Gson gson = new Gson();
	HashMap<String, String> keyValueheader = null;

	/**
	 * 1.3.1. The list of VIM Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/vims" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> lstVIMInstance(ServerHttpRequest request) throws IOException {
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.2. Create VIM Instance
	 * 
	 * @param nsd
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/vims" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> createVIMInstance(@RequestBody Object nsd, ServerHttpRequest request)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_VIM_INSTANCE);
		errHttpCode.setMes409(Constants.MESS_409_VIM_EXIST);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(nsd);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.3. Register to receive VIM's notification
	 * 
	 * @param nsd
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/vims/subscribe" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> registerVIMSubcribe(@RequestBody Object nsd, ServerHttpRequest request)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes303(Constants.MESS_303_VIM);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(nsd);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.4. Authenticate VIM User
	 * 
	 * @param nsd
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/vims/{vimId}/verify_vim" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> authenticateVIMUser(@RequestBody Object nsd, ServerHttpRequest request)
			throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_VIM_USER);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(nsd);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.5. Login VIM Instance
	 * 
	 * @param nsd
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/vims/{vimId}/validate_vim_user" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> loginVIMUser(@RequestBody Object nsd, ServerHttpRequest request) throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_VIM_INSTANCE);
		errHttpCode.setMes400(Constants.MESS_400_LOGIN_VIM);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(nsd);
		String path = request.getPath().toString().replace("/vocs-service", "");

		return nsdService.postObjectResultString(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.6. List of project depends on User
	 * 
	 * @param nsd
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/op_users/{opUserId}/projects" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> loginProjectOfUser(ServerHttpRequest request, @RequestHeader String openStackUserId)
			throws IOException {
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		Constants.setKeyParamHeader(keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.7. The detail of VIM Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/vims/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> detailVIMInstance(ServerHttpRequest request) throws IOException {
		Constants.setErrHttpCode(errHttpCode);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.8. Compute Quota
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/quotas/compute" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> viewComputeQuota(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.9. The rest of Compute Quota
	 */
	@RequestMapping(value = { "/api/v1/quotas/compute-left" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> viewComputeQuotaLeft(ServerHttpRequest request,
			@RequestHeader String openStackUserId, @RequestHeader String projectId) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.10. Xem Network Quota
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/quotas/network" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> viewNetworkQuota(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.11. Xem Network Quota còn lại
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/quotas/network-left" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> viewNetworkQuotaLeft(ServerHttpRequest request,
			@RequestHeader String openStackUserId, @RequestHeader String projectId) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.12. Xem Resource Quota
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/quotas/resource" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> viewResourceQuota(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.13. Thêm Project
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param ProjectInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/projects" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> addProject(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestBody Object ProjectInfo) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_PROJECT);
		errHttpCode.setMes409(Constants.MESS_409_PROJECT_EXIST);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(ProjectInfo);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	// --> từ đây api từ server test dều trả về 404

	/**
	 * 1.3.14. The list of User Openstack
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/op_users" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> lstOpUsers(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.15. Create User Openstack
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param ProjectInfo
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/op_users" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> createUserOpenStack(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestBody Object OpenStackUser) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_NSD_OPENSTK_USER);
		errHttpCode.setMes409(Constants.MESS_409_USER_EXIST);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(OpenStackUser);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.16. Update User Openstack
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param OpenStackUser
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/op_users/{id}" }, method = RequestMethod.PUT)
	@ResponseBody
	public Mono<ResponseEntity<?>> updateOpUsers(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestBody Object OpenStackUser) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_409_USER_EXIST);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(OpenStackUser);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.putObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.17. Delete User Openstack
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/op_users/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteUserOpenstack(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		// add message cho api
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.deleteObject(path).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.18. Assign User into project
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/projects/grant_to_op_users" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> assignUserToProject(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestBody Object obj) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.postArrayList(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.19. The detail of Project
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/projects/{projectId}" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> detailProject(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestHeader String vimId) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.20. Update Project
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param OpenStackUser
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/projects/{id}" }, method = RequestMethod.PUT)
	@ResponseBody
	public Mono<ResponseEntity<?>> updateProject(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestBody Object obj) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_PROJECT);
		errHttpCode.setMes409(Constants.MESS_409_PROJECT_EXIST);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.putObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.21. Delete project
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/projects/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteProject(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		// add message cho api
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.deleteObject(path).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.22. The list of Role
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/roles" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> lstRole(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.23. Create role
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/roles" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> addRole(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestBody Object obj) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_ROLE);
		errHttpCode.setMes409(Constants.MESS_409_ROLE_EXIST);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.24. Update role
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param OpenStackUser
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/roles/{id}" }, method = RequestMethod.PUT)
	@ResponseBody
	public Mono<ResponseEntity<?>> updateRoles(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId, @RequestBody Object OpenStackUser) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_UPDATE);
		errHttpCode.setMes409(Constants.MESS_409_ROLE_EXIST);
		Constants.setErrHttpCode(errHttpCode);
		String jsonObject = JsonUtils.convertObjectToJson(OpenStackUser);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.putObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.25. The detail of Role
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/roles/{id}" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> detailRole(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		String path = request.getPath().toString().replace("/vocs-service", "");
		return nsdService.getObject(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.26. Delete role
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/roles/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteRole(ServerHttpRequest request, @RequestHeader String openStackUserId,
			@RequestHeader String projectId) throws IOException {
		// add key-value header
		keyValueheader = new HashMap<String, String>();
		keyValueheader.put("openStackUserId", openStackUserId);
		keyValueheader.put("projectId", projectId);
		Constants.setKeyParamHeader(keyValueheader);
		// add message cho api
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());
		return nsdService.deleteObject(path).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.27. Delete Vim
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/vims/{id}" }, method = RequestMethod.DELETE)
	@ResponseBody
	public Mono<ResponseEntity<?>> deleteVIM(ServerHttpRequest request) throws IOException {
		errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);
		Constants.setErrHttpCode(errHttpCode);
		String path = DataUtils.repalcePath(request.getPath().toString());

		return nsdService.deleteObject(path).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.28. The list of UserMano
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/users" }, method = RequestMethod.GET)
	@ResponseBody
	public Mono<ResponseEntity<?>> lstUserMano(ServerHttpRequest request) throws IOException {
		String path = request.getPath().toString().replace("/vocs-service", "");

		return nsdService.getArrayList(path, null).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}

	/**
	 * 1.3.29. Assign VIM's permission for user
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = { "/api/v1/vims/{vimId}/grant_to_mano_user" }, method = RequestMethod.POST)
	@ResponseBody
	public Mono<ResponseEntity<?>> assigVimForUser(ServerHttpRequest request, @RequestBody Object obj)
			throws IOException {
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		String path = request.getPath().toString().replace("/vocs-service", "");

		return nsdService.postObject(path, jsonObject).map((result) -> {
			return ResponseEntity.ok(result);
		});
	}
}
