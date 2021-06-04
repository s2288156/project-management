package com.pm.controller;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.PidQuery;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.dto.cmd.ProjectDeleteCmd;
import com.pm.application.dto.cmd.ProjectDependAddCmd;
import com.pm.application.dto.query.ProjectPageQuery;
import com.pm.application.dto.vo.DependModuleVO;
import com.pm.application.dto.vo.ProjectVO;
import com.pm.application.service.IProjectService;
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
@RequestMapping("/project")
@RestController
public class ProjectController {

    @Autowired
    private IProjectService projectService;

    @PostMapping("/one")
    public SingleResponse<?> addOne(@Validated @RequestBody ProjectAddCmd addCmd) {
        return projectService.addOne(addCmd);
    }

    @GetMapping("/list")
    public PageResponse<ProjectVO> listProject(ProjectPageQuery projectPageQuery) {
        return projectService.listProjects(projectPageQuery);
    }

    @PostMapping("/depend")
    public Response dependAdd(@Validated @RequestBody ProjectDependAddCmd dependAddCmd) {
        return projectService.dependAdd(dependAddCmd);
    }

    @GetMapping("/depend/list")
    public PageResponse<DependModuleVO> listDepend(@Validated PidQuery pid) {
        return projectService.listDepend(pid);
    }

    @DeleteMapping("/depend")
    public Response deleteDepend(String id) {
        return projectService.deleteDepend(id);
    }

    @DeleteMapping
    public Response deleteProject(@Validated @RequestBody ProjectDeleteCmd cmd) {
        return projectService.deleteProject(cmd);
    }
}
