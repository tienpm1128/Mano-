package com.viettel.nfv.vdashboardx.communication;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IResourceManagementControllerPart2 {

    /**
     * 1.3.30. The list of VM based on Hypervisor
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = {"${resources.management.vims.id.users.id.projects.id.hypervisors.id.instances}" })
    @ResponseBody
    default Mono<ResultBO> lstVMByHypervisorApi(ServerHttpRequest request, @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return lstVMByHypervisor(request, authentication);
    }

    Mono<ResultBO> lstVMByHypervisor(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.31. Logout VIM instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.op-users.logout}" })
    @ResponseBody
    default Mono<ResultBO> logoutVMInstanceApi(ServerHttpRequest request, @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return logoutVMInstance(request, authentication);
    }

    Mono<ResultBO> logoutVMInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.32. The list of Software
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.softwares}" })
    @ResponseBody
    default Mono<ResultBO> lstSoftwareApi(ServerHttpRequest request, @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return lstSoftware(request, authentication);
    }

    Mono<ResultBO> lstSoftware(ServerHttpRequest request, String authentication) throws IOException;

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
    @PostMapping(value = { "${resources.management.softwares.upload}" }, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    default Mono<ResultBO> uploadSoftwareApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                             @RequestHeader String projectId, @RequestPart("filePart") Mono<FilePart> file,
                                             @RequestHeader(name = "Authorization") String authentication)
                                           throws IOException {
        return uploadSoftware(request, openStackUserId, projectId, file, authentication);
    }

    Mono<ResultBO> uploadSoftware(ServerHttpRequest request, String openStackUserId, String projectId, Mono<FilePart> file, String authentication)
            throws IOException;

    /**
     * 1.3.34. Update Software
     *
     * @param request
     * @param obj
     * @return
     * @throws IOException
     */
    @PutMapping(value = { "${resources.management.softwares.id.update}" })
    @ResponseBody
    default Mono<ResultBO> updateSoftwareApi(ServerHttpRequest request, @RequestBody Object obj,
                                             @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return updateSoftware(request, obj, authentication);
    }

    Mono<ResultBO> updateSoftware(ServerHttpRequest request, Object obj, String authentication) throws IOException;

    /**
     * 1.3.35. The detail of Software
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.softwares.id}" })
    @ResponseBody
    default Mono<ResultBO> detailSoftwareApi(ServerHttpRequest request,
                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return detailSoftware(request, authentication);
    }

    Mono<ResultBO> detailSoftware(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.36. The list of Image
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.images}" })
    @ResponseBody
    default Mono<ResultBO> lstImagesApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                        @RequestHeader String projectId,
                                        @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return lstImages(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> lstImages(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

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
    @PostMapping(value = { "${resources.management.images}" })
    @ResponseBody
    default Mono<ResultBO> createImageApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                         @RequestHeader String projectId, @RequestBody Object obj,
                                         @RequestHeader("file-path") String filePath,
                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return createImage(request, openStackUserId, projectId, obj, filePath, authentication);
    }

    Mono<ResultBO> createImage(ServerHttpRequest request, String openStackUserId,
                               String projectId, Object obj, String filePath, String authentication) throws IOException;

    /**
     * 1.3.38. Delete Image
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${resources.management.images.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteImageApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                          @RequestHeader String projectId,
                                          @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteImage(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> deleteImage(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

    /**
     * 1.3.39. The list of Network
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.networks}" })
    @ResponseBody
    default Mono<ResultBO> lstNetWorkApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                         @RequestHeader String projectId,
                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return lstNetWork(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> lstNetWork(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

    /**
     * 1.3.40. The list of Server Group
     *
     * @param request
     * @param openStackUserId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.server-groups}" })
    @ResponseBody
    default Mono<ResultBO> lstServerGroupApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                             @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return lstServerGroup(request, openStackUserId, authentication);
    }

    Mono<ResultBO> lstServerGroup(ServerHttpRequest request, String openStackUserId, String authentication) throws IOException;

    /**
     * 1.3.41. Create Server Group
     *
     * @param request
     * @param openStackUserId
     * @param obj
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${resources.management.server-groups}" })
    @ResponseBody
    default Mono<ResultBO> createServerGroupApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                @RequestBody Object obj,
                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return createServerGroup(request, openStackUserId, obj, authentication);
    }

    Mono<ResultBO> createServerGroup(ServerHttpRequest request, String openStackUserId, Object obj, String authentication) throws IOException;

    /**
     * 1.3.42. Delete Server Group
     *
     * @param request
     * @param openStackUserId
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${resources.management.server-groups.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteServerGroupApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return deleteServerGroup(request, openStackUserId, authentication);
    }

    Mono<ResultBO> deleteServerGroup(ServerHttpRequest request, String openStackUserId, String authentication) throws IOException;

    /**
     * 1.3.43. The list of UserMano
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.user-mgnt.users}" })
    @ResponseBody
    default Mono<ResultBO> getListOfUserManosApi(ServerHttpRequest request,
                                                 @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfUserManos(request, authentication);
    }

    Mono<ResultBO> getListOfUserManos(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.44. Create User Mano
     *
     * @param request
     * @param obj
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${resources.management.user-mgnt.users}" })
    @ResponseBody
    default Mono<ResultBO> createUserManoApi(ServerHttpRequest request, @RequestBody Object obj,
                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return createUserMano(request, obj, authentication);
    }

    Mono<ResultBO> createUserMano(ServerHttpRequest request, Object obj, String authentication) throws IOException;

    /**
     * 1.3.45. The detail of User Mano
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.user-mgnt.users.id}" })
    @ResponseBody
    default Mono<ResultBO> getDetailOfUserManoApi(ServerHttpRequest request,
                                                  @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getDetailOfUserMano(request, authentication);
    }

    Mono<ResultBO> getDetailOfUserMano(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.46. Update User Mano
     *
     * @param request
     * @param openStackUser
     * @return
     * @throws IOException
     */
    @PutMapping(value = { "${resources.management.user-mgnt.users.id}" })
    @ResponseBody
    default Mono<ResultBO> updateUserManoApi(ServerHttpRequest request, @RequestBody Object openStackUser,
                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return updateUserMano(request, openStackUser, authentication);
    }

    Mono<ResultBO> updateUserMano(ServerHttpRequest request, Object openStackUser, String authentication) throws IOException;

    /**
     * 1.3.47. Delete User Mano
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${resources.management.user-mgnt.users.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteUserManoApi(ServerHttpRequest request,
                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteUserMano(request, authentication);
    }

    Mono<ResultBO> deleteUserMano(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.48. Storage Quota
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.quotas.storage}" })
    @ResponseBody
    default Mono<ResultBO> viewStorageQuotaApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                               @RequestHeader String projectId,
                                               @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return viewStorageQuota(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> viewStorageQuota(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

    /**
     * 1.3.49. The rest of Storage Quota
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.quotas.storage-left}" })
    @ResponseBody
    default Mono<ResultBO> viewStorageQuotaLeftApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                   @RequestHeader String projectId,
                                                   @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return viewStorageQuotaLeft(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> viewStorageQuotaLeft(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

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
    @GetMapping(value = { "${resources.management.op-users.id}" })
    @ResponseBody
    default Mono<ResultBO> getUserOpenstackDetailApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                     @RequestHeader String projectId, @RequestBody Object openStackUser,
                                                     @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return getUserOpenstackDetail(request, openStackUserId, projectId, openStackUser, authentication);
    }

    Mono<ResultBO> getUserOpenstackDetail(ServerHttpRequest request, String openStackUserId,
                                          String projectId, Object openStackUser, String authentication) throws IOException;

    /**
     * 1.3.51. The total of Vims
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.vims.total}" })
    @ResponseBody
    default Mono<ResultBO> getTotalVimsApi(ServerHttpRequest request,
                                           @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getTotalVims(request, authentication);
    }

    Mono<ResultBO> getTotalVims(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.52. The total of Networks
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.networks.total}" })
    @ResponseBody
    default Mono<ResultBO> getTotalNetworksApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                               @RequestHeader String projectId, @RequestHeader String vimId,
                                               @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return getTotalNetworks(request, openStackUserId, projectId, vimId, authentication);
    }

    Mono<ResultBO> getTotalNetworks(ServerHttpRequest request, String openStackUserId,
                                    String projectId, String vimId, String authentication) throws IOException;

    /**
     * 1.5.53. The list of Tenants
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.user-mgnt.tenants}" })
    @ResponseBody
    default Mono<ResultBO> getTotalTenantsApi(ServerHttpRequest request,
                                              @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getTotalTenants(request, authentication);
    }

    Mono<ResultBO> getTotalTenants(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.54. Create Tenant
     *
     * @param request
     * @param obj
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${resources.management.user-mgnt.tenants}" })
    @ResponseBody
    default Mono<ResultBO> createTenantApi(ServerHttpRequest request, @RequestBody Object obj,
                                           @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return createTenant(request, obj, authentication);
    }

    Mono<ResultBO> createTenant(ServerHttpRequest request, Object obj, String authentication) throws IOException;

    /**
     * 1.3.55. The detail of Tenant
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.user-mgnt.tenants.id}" })
    @ResponseBody
    default Mono<ResultBO> getDetailOfTenantApi(ServerHttpRequest request,
                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getDetailOfTenant(request, authentication);
    }

    Mono<ResultBO> getDetailOfTenant(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.56. Delete Tenant
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${resources.management.user-mgnt.tenants.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteTenantApi(ServerHttpRequest request,
                                           @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteTenant(request, authentication);
    }

    Mono<ResultBO> deleteTenant(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.56. Get list of User Resource Quota
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.user-mtnt.reources.vims.id}" })
    @ResponseBody
    default Mono<ResultBO> getListOfUserResourceQuotaApi(ServerHttpRequest request,
                                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return getListOfUserResourceQuota(request, authentication);
    }

    Mono<ResultBO> getListOfUserResourceQuota(ServerHttpRequest request, String authentication) throws IOException;
}
