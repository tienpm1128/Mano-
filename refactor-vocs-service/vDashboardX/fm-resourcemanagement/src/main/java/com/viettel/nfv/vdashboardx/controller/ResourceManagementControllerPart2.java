package com.viettel.nfv.vdashboardx.controller;

import com.viettel.nfv.vdashboardx.communication.IResourceManagementControllerPart2;
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
import org.springframework.http.codec.multipart.FilePart;
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
@RequestMapping("/com.viettel.vocs-service")
@CrossOrigin(origins = { "http://localhost:8084" })
public class ResourceManagementControllerPart2 implements IResourceManagementControllerPart2 {

	@Autowired
	private IVimInstanceService vimInstanceService;

	@Autowired
	private IUserManoService userManoService;

	@Autowired
	private IQuotasService quotasService;

	@Autowired
	private IOpenstackUserService openstackUserService;

	@Autowired
	private ISoftwareService softwareService;

	@Autowired
	private IImageService imageService;

	@Autowired
	private INetworkService networkService;

	@Autowired
	private IServerGroupService serverGroupService;

	@Autowired
	private ITenantService tenantService;

	private static final Logger logger = LoggerFactory.getLogger(ResourceManagementControllerPart2.class.getName());

	/**
	 * 1.3.30. The list of VM based on Hypervisor
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> lstVMByHypervisor(ServerHttpRequest request, String authentication) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		return this.vimInstanceService.getListOfVimsByHypervisorId(path, requestContent);
	}

	/**
	 * 1.3.31. Logout VIM instance
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> logoutVMInstance(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204("Success!");

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.vimInstanceService.logoutVimInstance(path, requestContent);
	}

	/**
	 * 1.3.32. The list of Software
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> lstSoftware(ServerHttpRequest request, String authentication) throws IOException {
		String path = DataUtils.repalcePath(request.getPath().toString());
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		return this.softwareService.getListOfSoftwares(path, requestContent);
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
	public Mono<ResultBO> uploadSoftware(ServerHttpRequest request, String openStackUserId,
										 String projectId, Mono<FilePart> file, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		long fileSize = request.getHeaders().getContentLength();

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_UPLOAD_SOFT);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.headerParams(keyValueheader)
				.filePart(file)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.softwareService.uploadSoftware(path, requestContent, fileSize);
	}

	/**
	 * 1.3.34. Update Software
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> updateSoftware(ServerHttpRequest request, Object obj, String authentication)
			throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes202(Constants.MESS_202_UPDATE);

		String jsonObject = JsonUtils.convertObjectToJson(obj);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.softwareService.updateSoftware(path, requestContent);
	}

	/**
	 * 1.3.35. The detail of Software
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> detailSoftware(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.softwareService.getSoftwareDetail(path, requestContent);
	}

	/**
	 * 1.3.36. The list of Image
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> lstImages(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.imageService.getListOfImages(path, requestContent);
	}

	/**
	 * 1.3.37. Create Image
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @param obj
	 * @param filePath
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createImage(ServerHttpRequest request, String openStackUserId,
									  String projectId, Object obj, String filePath, String authentication) throws IOException {
		Map<String, String> keyValueheader = new HashMap();
		keyValueheader.put(Constants.OPENSTACK_USERID, openStackUserId);
		keyValueheader.put(Constants.PROJECT_ID, projectId);
		keyValueheader.put(Constants.FILEPATH, filePath);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_IMG);
		errHttpCode.setMes409(Constants.MESS_409_IMG_EXIST);

		String jsonObject = JsonUtils.convertObjectToJson(obj);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.imageService.createImage(path, requestContent);
	}

	/**
	 * 1.3.38. Delete Image
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteImage(ServerHttpRequest request, String openStackUserId,
									  String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		logger.info("Path of deleteImage: {}", path);
		return this.imageService.deleteImage(path, requestContent);
	}

	/**
	 * 1.3.39. The list of Network
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param projectId
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> lstNetWork(ServerHttpRequest request, String openStackUserId,
									 String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.networkService.getListOfNetworks(path, requestContent);
	}

	/**
	 * 1.3.40. The list of Server Group
	 * 
	 * @param request
	 * @param openStackUserId
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> lstServerGroup(ServerHttpRequest request, String openStackUserId, String authentication) throws IOException {
		Map<String, String> keyValueheader = new HashMap();
		keyValueheader.put(Constants.OPENSTACK_USERID, openStackUserId);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.serverGroupService.getListOfServerGroups(path, requestContent);
	}

	/**
	 * 1.3.41. Create Server Group
	 * 
	 * @param request
	 * @param openStackUserId
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createServerGroup(ServerHttpRequest request, String openStackUserId,
											Object obj, String authentication) throws IOException {
		Map<String, String> keyValueheader = new HashMap();
		keyValueheader.put(Constants.OPENSTACK_USERID, openStackUserId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_SERVER_GROUP);
		errHttpCode.setMes409(Constants.MESS_409_SERVER_GROUP);

		String jsonObject = JsonUtils.convertObjectToJson(obj);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.bodyData(jsonObject)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.serverGroupService.createServerGroup(path, requestContent);
	}

	/**
	 * 1.3.42. Delete Server Group
	 * 
	 * @param request
	 * @param openStackUserId
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteServerGroup(ServerHttpRequest request, String openStackUserId, String authentication)
			throws IOException {
		Map<String, String> keyValueheader = new HashMap();
		keyValueheader.put(Constants.OPENSTACK_USERID, openStackUserId);

		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.serverGroupService.deleteServerGroup(path, requestContent);
	}

	/**
	 * 1.3.43. The list of UserMano
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfUserManos(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.userManoService.getListOfUserManos(path, requestContent);
	}

	/**
	 * 1.3.44. Create User Mano
	 * 
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createUserMano(ServerHttpRequest request, Object obj, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes201(Constants.MESS_201_SUCCESS);
		errHttpCode.setMes409(Constants.MESS_409_USER_EXIST);

		String jsonObject = JsonUtils.convertObjectToJson(obj);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.userManoService.createUserMano(path, requestContent);
	}

	/**
	 * 1.3.45. The detail of User Mano
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getDetailOfUserMano(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.userManoService.getUserManoDetail(path, requestContent);
	}

	/**
	 * 1.3.46. Update User Mano
	 * 
	 * @param request
	 * @param openStackUser
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> updateUserMano(ServerHttpRequest request, Object openStackUser, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_409_USER_EXIST);
		errHttpCode.setMes202(Constants.MESS_202_UPDATE);

		String jsonObject = JsonUtils.convertObjectToJson(openStackUser);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.userManoService.updateUserMano(path, requestContent);
	}

	/**
	 * 1.3.47. Delete User Mano
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteUserMano(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.userManoService.deleteUserMano(path, requestContent);
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
	public Mono<ResultBO> viewStorageQuota(ServerHttpRequest request, String openStackUserId,
										   String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.quotasService.viewStorageQuota(path, requestContent);
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
	public Mono<ResultBO> viewStorageQuotaLeft(ServerHttpRequest request, String openStackUserId,
											   String projectId, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.quotasService.viewStorageQuotaLeft(path, requestContent);
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
	public Mono<ResultBO> getUserOpenstackDetail(ServerHttpRequest request, String openStackUserId,
												 String projectId, Object openStackUser, String authentication) throws IOException {
		Map<String, String> keyValueheader = DataUtils.createHeaderParams(openStackUserId, projectId);
		String jsonObject = JsonUtils.convertObjectToJson(openStackUser);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.openstackUserService.getOpenstackUserDetail(path, requestContent);
	}

	/**
	 * 1.3.51. The total of Vims
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getTotalVims(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.vimInstanceService.getTotalVims(path, requestContent);
	}

	/**
	 * 1.3.52. The total of Networks
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getTotalNetworks(ServerHttpRequest request, String openStackUserId,
										   String projectId, String vimId, String authentication) throws IOException {
		Map<String, String> keyValueheader = new HashMap<>();
		keyValueheader.put(Constants.OPENSTACK_USERID, openStackUserId);
		keyValueheader.put(Constants.PROJECT_ID, projectId);
		keyValueheader.put(Constants.VIMID, vimId);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.headerParams(keyValueheader)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.networkService.getTotalNetworks(path, requestContent);
	}

	/**
	 * 1.3.53. The list of Tenants
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getTotalTenants(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.tenantService.getListOfTenants(path, requestContent);
	}

	/**
	 * 1.3.54. Create Tenant
	 *
	 * @param request
	 * @param obj
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> createTenant(ServerHttpRequest request, Object obj, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes409(Constants.MESS_409_TENANT_EXIST);
		errHttpCode.setMes202(Constants.MESS_201_SUCCESS);

		String jsonObject = JsonUtils.convertObjectToJson(obj);
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.errorCodes(errHttpCode)
				.bodyData(jsonObject)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.tenantService.createTenant(path, requestContent);
	}

	/**
	 * 1.3.55. The detail of Tenant
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getDetailOfTenant(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.tenantService.getTenantDetail(path, requestContent);
	}

	/**
	 * 1.3.56. Delete Tenant
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> deleteTenant(ServerHttpRequest request, String authentication) throws IOException {
		ErrHttpCode errHttpCode = new ErrHttpCode();
		errHttpCode.setMes204(Constants.MESS_204_DELETE);

		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.tenantService.deleteTenant(path, requestContent);
	}

	/**
	 * 1.3.56. Get list of User Resource Quota
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public Mono<ResultBO> getListOfUserResourceQuota(ServerHttpRequest request, String authentication) throws IOException {
		RequestContent requestContent = RequestContent.builder()
				.authenticationBearer(authentication)
				.build();

		String path = DataUtils.repalcePath(request.getPath().toString());
		return this.vimInstanceService.getListOfUserResourceQuota(path, requestContent);
	}
}
