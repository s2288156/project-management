package com.pm.controller;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.GroupId;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.dto.vo.ProjectVO;
import com.pm.application.service.IProjectService;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wcy
 */
@RequestMapping("/project")
@RestController
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @PostMapping("/one")
    public SingleResponse<?> addOne(@Validated @RequestBody ProjectAddCmd addCmd) {
        return projectService.addOne(addCmd);
    }

    @GetMapping("/list/for_group")
    public PageResponse<ProjectVO> listByGroup(GroupId groupId) {
        return projectService.listByGroupId(groupId);
    }

    @GetMapping("/list")
    public PageResponse<ProjectVO> list(PageQuery pageQuery) {
        return projectService.listAll(pageQuery);
    }

}
