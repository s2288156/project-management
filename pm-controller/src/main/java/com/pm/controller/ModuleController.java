package com.pm.controller;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.cmd.ModulePageQueryCmd;
import com.pm.application.dto.cmd.ModuleVersionAddCmd;
import com.pm.application.dto.cmd.ModuleVersionPageQueryCmd;
import com.pm.application.dto.cmd.ModuleVersionUpdateCmd;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.application.dto.vo.ModuleVersionVO;
import com.pm.application.service.IModuleService;
import com.pm.infrastructure.entity.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wcy
 */
@RequestMapping("/module")
@RestController
public class ModuleController {

    @Autowired
    private IModuleService moduleService;

    @PostMapping("/one")
    public SingleResponse<?> addOne(@Validated @RequestBody ModuleAddCmd moduleAddCmd) {
        return moduleService.addOne(moduleAddCmd);
    }

    @GetMapping("/list")
    public PageResponse<ModuleVO> listModule(ModulePageQueryCmd modulePageQueryCmd) {
        return moduleService.list(modulePageQueryCmd);
    }

    @PostMapping("/version")
    public Response addVersion(@Validated @RequestBody ModuleVersionAddCmd versionAddCmd) {
        return moduleService.addVersion(versionAddCmd);
    }

    @GetMapping("/version/list")
    public PageResponse<ModuleVersionVO> listModuleVersion(@Validated ModuleVersionPageQueryCmd versionPageQueryCmd) {
        return moduleService.listVersion(versionPageQueryCmd);
    }

    @PutMapping("/version")
    public Response updateVersion(@Validated @RequestBody ModuleVersionUpdateCmd versionUpdateCmd) {
        return null;
    }
}
