package com.pm.application.service;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.RoleAddCmd;
import com.pm.application.dto.cmd.RoleSetResourcesCmd;
import com.pm.application.dto.query.UserRolesQuery;
import com.pm.application.dto.vo.RoleVO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;

/**
 * @author wcy
 */
public interface IRoleService {

    PageResponse<RoleVO> pageRole(PageQuery pageQuery);

    SingleResponse<String> addRole(RoleAddCmd roleAddCmd);

    Response deleteRole(String id);

    MultiResponse<RoleVO> listRoleByUid(UserRolesQuery userRolesQuery);

    Response setResources(RoleSetResourcesCmd setResourcesCmd);
}
