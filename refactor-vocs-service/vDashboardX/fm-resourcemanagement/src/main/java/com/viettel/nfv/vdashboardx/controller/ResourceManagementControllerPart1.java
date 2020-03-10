package com.viettel.nfv.vdashboardx.controller;

import com.viettel.nfv.vdashboardx.communication.IResourceManagementControllerPart1;
import com.viettel.nfv.vdashboardx.exception.ErrHttpCode;
import com.viettel.nfv.vdashboardx.request.RequestContent;
import com.viettel.nfv.vdashboardx.response.ResultBO;
import com.viettel.nfv.vdashboardx.response.ResultObjectBO;
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
import java.util.HashMap;
import java.util.Map;

@RestController
@PropertySource(value = "classpath:/ws-resource.properties", ignoreResourceNotFound = true)
@RequestMapping("${vocs-service.common}")
@CrossOrigin(origins = "http://localhost:8084")
public class ResourceManagementControllerPart1 implements IResourceManagementControllerPart1 {

	@Autowired
	private IVimInstanceService vimInstanceService;

	@Autowired
	private IProjectService projectService;

	@Autowired
	private IQuotasService quotasService;

	@Autowired
	private IOpenstackUserService openstackUserService;

	@Autowired
	private IRoleService roleService;

	@Autowired
	private IUserManoService userManoService;

	private static final Logger logger = LoggerFactory.getLogger(ResourceManagementControllerPart1.class.getName());

	/**
	 * 1.3.1. The list of VIM Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> lstVIMInstance(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());
		return vimInstanceService.getListOfVimInstances(path, requestContent);
	}

	/**
	 * 1.3.2. Create VIM Instance
	 * 
	 * @param nsd
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createVIMInstance(Object nsd, ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_VIM_INSTANCE);
		errHttpCode.setMes409(Constants.MESS_409_VIM_EXIST);

		String jsonObject = JsonUtils.convertObjectToJson(nsd);
		String path = DataUtils.repalcePath(request.getPath().toString());

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.errorCodes(errHttpCode)
				.build();

		return vimInstanceService.createVimInstance(path, requestContent);
	}

	/**
	 * 1.3.3. Register to receive the VIM's notifications
	 * 
	 * @param nsd
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> registerVIMSubcribe(Object nsd, ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes303(Constants.MESS_303_VIM);

		String jsonObject = JsonUtils.convertObjectToJson(nsd);
		String path = DataUtils.repalcePath(request.getPath().toString());

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.errorCodes(errHttpCode)
				.build();

		return vimInstanceService.receiveVimNotification(path, requestContent);
	}

	/**
	 * 1.3.4. Authenticate VIM User
	 * 
	 * @param nsd
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> authenticateVIMUser(Object nsd, ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_VIM_USER);

		String jsonObject = JsonUtils.convertObjectToJson(nsd);
		String path = DataUtils.repalcePath(request.getPath().toString());

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.errorCodes(errHttpCode)
				.build();

		Mono<ResultBO> result = vimInstanceService.authenticateVimUser(path, requestContent);
		return result.map(item -> {
			if (item.getMessage().contains("Not permission")) {
				ResultObjectBO objectBO = new ResultObjectBO();
				objectBO.setError(true);
				objectBO.setErrorCode(Constants.N_OK_401);
				objectBO.setMessage(Constants.MESS_401_VALIDATE_VIM_USER);

				return objectBO;
			}

			return item;
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
	public Mono<ResultBO> loginVIMUser(Object nsd, ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_VIM_INSTANCE);
		errHttpCode.setMes400(Constants.MESS_400_LOGIN_VIM);

		String jsonObject = JsonUtils.convertObjectToJson(nsd);
		String path = DataUtils.repalcePath(request.getPath().toString());

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.errorCodes(errHttpCode)
				.build();

		return vimInstanceService.loginVimInstance(path, requestContent);
	}

	/**
	 * 1.3.6. List of project depends on User
	 * 
	 * @param request: ServerHttpRequest
	 * @param openStackUserId: String
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> loginProjectOfUser(ServerHttpRequest request, String openStackUserId, String authentication) throws IOException {
		HashMap<String, String> keyValueheader = new HashMap<>();
		keyValueheader.put(Constants.OPENSTACK_USERID, openStackUserId);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return projectService.getListOfProjectsByUserId(path, requestContent);
	}

	/**
	 * 1.3.7. The detail of VIM Instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> detailVIMInstance(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();
		String path = DataUtils.repalcePath(request.getPath().toString());
		return vimInstanceService.getVimInstanceDetail(path, requestContent);
	}

	/**
	 * 1.3.8. Compute Quota
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> viewComputeQuota(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return quotasService.viewComputeQuota(path, requestContent);
	}

	/**
	 * 1.3.9. The rest of Compute Quota
	 */
	public Mono<ResultBO> viewComputeQuotaLeft(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return quotasService.viewComputeQuotaLeft(path, requestContent);
	}

	/**
	 * 1.3.10. View Network Quota
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> viewNetworkQuota(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return quotasService.viewNetworkQuota(path, requestContent);
	}

	/**
	 * 1.3.11. View the rest of Network Quota
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> viewNetworkQuotaLeft(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return quotasService.viewNetworkQuotaLeft(path, requestContent);
	}

	/**
	 * 1.3.12. View Resource Quota
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> viewResourceQuota(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return quotasService.viewResourceQuota(path, requestContent);
	}

	/**
	 * 1.3.13. Create Project
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param projectInfo
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> addProject(ServerHttpRequest request, String openStackUserId, String projectId, Object projectInfo, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_PROJECT);
		errHttpCode.setMes409(Constants.MESS_409_PROJECT_EXIST);

		String jsonObject = JsonUtils.convertObjectToJson(projectInfo);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return projectService.createProject(path, requestContent);
	}

	/**
	 * 1.3.14. The list of user openstack
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> lstOpUsers(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return openstackUserService.getListOfOpenstackUser(path, requestContent);
	}

	/**
	 * 1.3.15. Create User Openstack
	 * 
	 * @param request: ServerHttpRequest
	 * @param openStackUserId: String
	 * @param projectId: String
	 * @param openStackUser: Object
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createUserOpenStack(ServerHttpRequest request, String openStackUserId, String projectId, Object openStackUser, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_NSD_OPENSTK_USER);
		errHttpCode.setMes409(Constants.MESS_409_USER_EXIST);

		String jsonObject = JsonUtils.convertObjectToJson(openStackUser);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());

		return openstackUserService.createOpenstackUser(path, requestContent);
	}

	/**
	 * 1.3.16. Update User Openstack
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param openStackUser
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> updateOpUsers(ServerHttpRequest request, String openStackUserId, String projectId, Object openStackUser, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_409_USER_EXIST);

		String jsonObject = JsonUtils.convertObjectToJson(openStackUser);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return openstackUserService.updateOpenstackUser(path, requestContent);
	}

	/**
	 * 1.3.17. Delete User Openstack
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteUserOpenstack(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		logger.info("Path of deleteUserOpenstack:{}", path);
		return openstackUserService.deleteOpenstackUser(path, requestContent);
	}

	/**
	 * 1.3.18. Assign user into project
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> assignUserToProject(ServerHttpRequest request, String openStackUserId, String projectId, Object obj, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return projectService.assignUserToProject(path, requestContent);
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
	public Mono<ResultBO> detailProject(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return projectService.getProjectDetail(path, requestContent);
	}

	/**
	 * 1.3.20. Update Project
	 * 
	 * @param request: ServerHttpRequest
	 * @param openStackUserId: String
	 * @param projectId: String
	 * @param obj: Object
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> updateProject(ServerHttpRequest request, String openStackUserId, String projectId, Object obj, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_PROJECT);
		errHttpCode.setMes409(Constants.MESS_409_PROJECT_EXIST);

		String jsonObject = JsonUtils.convertObjectToJson(obj);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return projectService.updateProject(path, requestContent);
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
	public Mono<ResultBO> deleteProject(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		logger.info("Path of deleteProject: {}", path);
		return projectService.deleteProject(path, requestContent);
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
	public Mono<ResultBO> lstRole(ServerHttpRequest request,  String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return roleService.getListOfRoles(path, requestContent);
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
	public Mono<ResultBO> addRole(ServerHttpRequest request,  String openStackUserId, String projectId, Object obj, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_ROLE);
		errHttpCode.setMes409(Constants.MESS_409_ROLE_EXIST);

		String jsonObject = JsonUtils.convertObjectToJson(obj);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.bodyData(jsonObject)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return roleService.createRole(path, requestContent);
	}

	/**
	 * 1.3.24. Update role
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param openStackUser
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> updateRoles(ServerHttpRequest request,  String openStackUserId, String projectId,  Object openStackUser, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_UPDATE);
		errHttpCode.setMes409(Constants.MESS_409_ROLE_EXIST);

		String jsonObject = JsonUtils.convertObjectToJson(openStackUser);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.bodyData(jsonObject)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return roleService.updateRole(path, requestContent);
	}

	/**
	 * 1.3.25. The detail of role
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> detailRole(ServerHttpRequest request,  String openStackUserId,
			 String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return roleService.getRoleDetail(path, requestContent);
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
	public Mono<ResultBO> deleteRole(ServerHttpRequest request,  String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		logger.info("Path of deleteRole:{}", path);
		return roleService.deleteRole(path, requestContent);
	}

	/**
	 * 1.3.27. Delete Vim
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteVIM(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		logger.info("Path of deleteVIM: {}", path);
		return vimInstanceService.deleteVim(path, requestContent);
	}

	/**
	 * 1.3.28. The list of user Mano
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> lstUserMano(ServerHttpRequest request, String authentication) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		return userManoService.getListOfUserManos(path, requestContent);
	}

	/**
	 * 1.3.29. Assign VIM's permission to user
	 * 
	 * @param request: ServerHttpRequest
	 * @param obj: Object
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> assigVimForUser(ServerHttpRequest request,  Object obj, String authentication)
			throws IOException {
		String jsonObject = JsonUtils.convertObjectToJson(obj);
		String path = DataUtils.repalcePath(request.getPath().toString());
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.bodyData(jsonObject)
				.build();

		return vimInstanceService.assignVimForUser(path, requestContent);
	}

}
