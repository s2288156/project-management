package com.pm.controller;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
}
