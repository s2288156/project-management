package com.pm.controller;

import com.pm.application.dto.vo.RoleVO;
import com.pm.application.service.IRoleService;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
}
