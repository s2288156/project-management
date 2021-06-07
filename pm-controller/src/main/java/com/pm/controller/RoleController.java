package com.pm.controller;

import com.alibaba.cola.dto.MultiResponse;
import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.RoleAddCmd;
import com.pm.application.dto.cmd.RoleSetResourcesCmd;
import com.pm.application.dto.query.UserRolesQuery;
import com.pm.application.dto.vo.RoleVO;
import com.pm.application.service.IRoleService;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wcy
 */
@RequestMapping("/role")
@RestController
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @GetMapping("/list")
    public PageResponse<RoleVO> pageRole(PageQuery pageQuery) {
        return roleService.pageRole(pageQuery);
    }

    @GetMapping("/list/by_uid")
    public MultiResponse<RoleVO> listRolesByUid(UserRolesQuery userRolesQuery) {
        return roleService.listRoleByUid(userRolesQuery);
    }

    @PostMapping
    public SingleResponse<String> addRole(@Validated @RequestBody RoleAddCmd roleAddCmd) {
        return roleService.addRole(roleAddCmd);
    }

    @DeleteMapping
    public Response deleteRole(String id) {
        return roleService.deleteRole(id);
    }

    @PostMapping("/resources")
    public Response setResources(@Validated @RequestBody RoleSetResourcesCmd setResourcesCmd) {
        return roleService.setResources(setResourcesCmd);
    }

}
