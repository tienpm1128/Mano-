package com.viettel.nfv.vdashboardx.communication;

import com.viettel.nfv.vdashboardx.response.ResultBO;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.io.IOException;

public interface IResourceManagementControllerPart1 {

    /**
     * 1.3.1. The list of VIM Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.vims}" })
    @ResponseBody
    default Mono<ResultBO> lstVIMInstanceApi(ServerHttpRequest request,
                                             @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return lstVIMInstance(request, authentication);
    }

    Mono<ResultBO> lstVIMInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.2. Create VIM Instance
     *
     * @param nsd
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${resources.management.vims}" })
    @ResponseBody
    default Mono<ResultBO> createVIMInstanceApi(@RequestBody Object nsd, ServerHttpRequest request,
                                                @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return createVIMInstance(nsd, request, authentication);
    }

    Mono<ResultBO> createVIMInstance(Object nsd, ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.3. Register VIM to receive notifications
     *
     * @param nsd
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${resources.management.vims.subscribe}" })
    @ResponseBody
    default Mono<ResultBO> registerVIMSubcribeApi(@RequestBody Object nsd, ServerHttpRequest request,
                                                  @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return registerVIMSubcribe(nsd, request, authentication);
    }

    Mono<ResultBO> registerVIMSubcribe(Object nsd, ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.4. Authenticate VIM User
     *
     * @param nsd
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${resources.management.vims.id.verify}" })
    @ResponseBody
    default Mono<ResultBO> authenticateVIMUserApi(@RequestBody Object nsd, ServerHttpRequest request,
                                                  @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return authenticateVIMUser(nsd, request, authentication);
    }

    Mono<ResultBO> authenticateVIMUser(Object nsd, ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.5. Login VIM Instance
     *
     * @param nsd
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${resources.management.vims.id.validate}" })
    @ResponseBody
    default Mono<ResultBO> loginVIMUserApi(@RequestBody Object nsd, ServerHttpRequest request,
                                           @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return loginVIMUser(nsd, request, authentication);
    }

    Mono<ResultBO> loginVIMUser(Object nsd, ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.6. List of project depends on User
     *
     * @param request: ServerHttpRequest
     * @param openStackUserId: String
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.op-users.id.project}" })
    @ResponseBody
    default Mono<ResultBO> loginProjectOfUserApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                 @RequestHeader(name = "Authorization") String authentication)
            throws IOException {
        return loginProjectOfUser(request, openStackUserId, authentication);
    }

    Mono<ResultBO> loginProjectOfUser(ServerHttpRequest request, String openStackUserId, String authentication) throws IOException;

    /**
     * 1.3.7. The detail of VIM Instance
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.vims.id}" })
    @ResponseBody
    default Mono<ResultBO> detailVIMInstanceApi(ServerHttpRequest request,
                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return detailVIMInstance(request, authentication);
    }

    Mono<ResultBO> detailVIMInstance(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.8. Compute Quota
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.quotas.compute}" })
    @ResponseBody
    default Mono<ResultBO> viewComputeQuotaApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                               @RequestHeader String projectId,
                                               @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return viewComputeQuota(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> viewComputeQuota(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

    /**
     * 1.3.9. The rest of Compute Quota
     */
    @GetMapping(value = { "${resources.management.quotas.compute-left}" })
    @ResponseBody
    default Mono<ResultBO> viewComputeQuotaLeftApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                @RequestHeader String projectId,
                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return viewComputeQuotaLeft(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> viewComputeQuotaLeft(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

    /**
     * 1.3.10. View Network Quota
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.quotas.network}" })
    @ResponseBody
    default Mono<ResultBO> viewNetworkQuotaApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                               @RequestHeader String projectId,
                                               @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return viewNetworkQuota(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> viewNetworkQuota(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

    /**
     * 1.3.11. View the rest of Network Quota
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.quotas.network-left}" })
    @ResponseBody
    default Mono<ResultBO> viewNetworkQuotaLeftApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                   @RequestHeader String projectId,
                                                   @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return viewNetworkQuotaLeft(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> viewNetworkQuotaLeft(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

    /**
     * 1.3.12. View Resource Quota
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.quotas.resource}" })
    @ResponseBody
    default Mono<ResultBO> viewResourceQuotaApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                @RequestHeader String projectId,
                                                @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return viewResourceQuota(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> viewResourceQuota(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

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
    @PostMapping(value = { "${resources.management.projects}" })
    @ResponseBody
    default Mono<ResultBO> addProjectApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                         @RequestHeader String projectId, @RequestBody Object projectInfo,
                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return addProject(request, openStackUserId, projectId, projectInfo, authentication);
    }

    Mono<ResultBO> addProject(ServerHttpRequest request, String openStackUserId, String projectId, Object projectInfo, String authentication) throws IOException;

    /**
     * 1.3.14. The list of user openstack
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.op-users}" })
    @ResponseBody
    default Mono<ResultBO> lstOpUsersApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                         @RequestHeader String projectId,
                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return lstOpUsers(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> lstOpUsers(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

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
    @PostMapping(value = { "${resources.management.op-users}" })
    @ResponseBody
    default Mono<ResultBO> createUserOpenStackApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                  @RequestHeader String projectId, @RequestBody Object openStackUser,
                                                  @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return createUserOpenStack(request, openStackUserId, projectId, openStackUser, authentication);
    }

    Mono<ResultBO> createUserOpenStack(ServerHttpRequest request, String openStackUserId, String projectId, Object openStackUser, String authentication) throws IOException;

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
    @PutMapping(value = { "${resources.management.op-users.id}" })
    @ResponseBody
    default Mono<ResultBO> updateOpUsersApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                            @RequestHeader String projectId, @RequestBody Object openStackUser,
                                            @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return updateOpUsers(request, openStackUserId, projectId, openStackUser, authentication);
    }

    Mono<ResultBO> updateOpUsers(ServerHttpRequest request, String openStackUserId, String projectId, Object openStackUser, String authentication) throws IOException;

    /**
     * 1.3.17. Delete User Openstack
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${resources.management.op-users.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteUserOpenstackApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                  @RequestHeader String projectId,
                                                  @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteUserOpenstack(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> deleteUserOpenstack(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

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
    @PostMapping(value = { "${resources.management.projects.grant-user}" })
    @ResponseBody
    default Mono<ResultBO> assignUserToProjectApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                                  @RequestHeader String projectId, @RequestBody Object obj,
                                                  @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return assignUserToProject(request, openStackUserId, projectId, obj, authentication);
    }

    Mono<ResultBO> assignUserToProject(ServerHttpRequest request, String openStackUserId, String projectId, Object obj, String authentication) throws IOException;

    /**
     * 1.3.19. The detail of Project
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.projects.id}" })
    @ResponseBody
    default Mono<ResultBO> detailProjectApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                            @RequestHeader String projectId,
                                            @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return detailProject(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> detailProject(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

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
    @PutMapping(value = { "${resources.management.projects.id}" })
    @ResponseBody
    default Mono<ResultBO> updateProjectApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                            @RequestHeader String projectId, @RequestBody Object obj,
                                            @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return updateProject(request, openStackUserId, projectId, obj, authentication);
    }

    Mono<ResultBO> updateProject(ServerHttpRequest request, String openStackUserId, String projectId, Object obj, String authentication) throws IOException;

    /**
     * 1.3.21. Xóa project
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${resources.management.projects.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteProjectApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                            @RequestHeader String projectId,
                                            @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteProject(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> deleteProject(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

    /**
     * 1.3.22. The rest of Role
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.roles}" })
    @ResponseBody
    default Mono<ResultBO> lstRoleApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                      @RequestHeader String projectId,
                                      @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return lstRole(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> lstRole(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

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
    @PostMapping(value = { "${resources.management.roles}" })
    @ResponseBody
    default Mono<ResultBO> addRoleApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                      @RequestHeader String projectId, @RequestBody Object obj,
                                      @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return addRole(request, openStackUserId, projectId, obj, authentication);
    }

    Mono<ResultBO> addRole(ServerHttpRequest request, String openStackUserId, String projectId, Object obj, String authentication) throws IOException;

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
    @PutMapping(value = { "${resources.management.roles.id}" })
    @ResponseBody
    default Mono<ResultBO> updateRolesApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                          @RequestHeader String projectId, @RequestBody Object openStackUser,
                                          @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return updateRoles(request, openStackUserId, projectId, openStackUser, authentication);
    }

    Mono<ResultBO> updateRoles(ServerHttpRequest request, String openStackUserId, String projectId, Object openStackUser, String authentication) throws IOException;

    /**
     * 1.3.25. The detail of role
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.roles.id}" })
    @ResponseBody
    default Mono<ResultBO> detailRoleApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                         @RequestHeader String projectId,
                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return detailRole(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> detailRole(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

    /**
     * 1.3.26. Delete role
     *
     * @param request
     * @param openStackUserId
     * @param projectId
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${resources.management.roles.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteRoleApi(ServerHttpRequest request, @RequestHeader String openStackUserId,
                                         @RequestHeader String projectId,
                                         @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteRole(request, openStackUserId, projectId, authentication);
    }

    Mono<ResultBO> deleteRole(ServerHttpRequest request, String openStackUserId, String projectId, String authentication) throws IOException;

    /**
     * 1.3.27. Delete Vim
     *
     * @param request
     * @return
     * @throws IOException
     */
    @DeleteMapping(value = { "${resources.management.vims.id}" })
    @ResponseBody
    default Mono<ResultBO> deleteVIMApi(ServerHttpRequest request,
                                        @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return deleteVIM(request, authentication);
    }

    Mono<ResultBO> deleteVIM(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.28. The list of user Mano
     *
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping(value = { "${resources.management.mano-users}" })
    @ResponseBody
    default Mono<ResultBO> lstUserManoApi(ServerHttpRequest request,
                                          @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return lstUserMano(request, authentication);
    }

    Mono<ResultBO> lstUserMano(ServerHttpRequest request, String authentication) throws IOException;

    /**
     * 1.3.29. Assign permission of VIM to user
     *
     * @param request: ServerHttpRequest
     * @param obj: Object
     * @return
     * @throws IOException
     */
    @PostMapping(value = { "${resources.management.vims.id.grant-user-mano}" })
    @ResponseBody
    default Mono<ResultBO> assigVimForUserApi(ServerHttpRequest request, @RequestBody Object obj,
                                              @RequestHeader(name = "Authorization") String authentication) throws IOException {
        return assigVimForUser(request, obj, authentication);
    }

    Mono<ResultBO> assigVimForUser(ServerHttpRequest request, Object obj, String authentication) throws IOException;

}
