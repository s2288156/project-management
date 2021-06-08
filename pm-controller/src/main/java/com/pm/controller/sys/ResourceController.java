package com.pm.controller.sys;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.ResourceAddCmd;
import com.pm.application.dto.query.RoleResourcePageQuery;
import com.pm.application.dto.vo.ResourceVO;
import com.pm.application.service.IResourceService;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@RequestMapping("/resource")
@RestController
public class ResourceController {

    @Autowired
    private IResourceService resourceService;

    @PostMapping
    public SingleResponse<String> addResource(@RequestBody @Validated ResourceAddCmd addCmd) {
        return SingleResponse.of(resourceService.addResource(addCmd));
    }

    @GetMapping("/list")
    public PageResponse<ResourceVO> pageResource(@Validated PageQuery pageQuery) {
        return resourceService.pageResource(pageQuery);
    }

    @GetMapping("/list/by_role")
    public PageResponse<ResourceVO> pageRoleResource(@Validated RoleResourcePageQuery pageQuery) {
        return resourceService.pageRoleResource(pageQuery);
    }

    @DeleteMapping
    public Response deleteResource(String id) {
        resourceService.deleteById(id);
        return Response.buildSuccess();
    }
}
