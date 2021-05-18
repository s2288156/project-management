package com.pm.controller;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.*;
import com.pm.application.dto.query.ModulePageQuery;
import com.pm.application.dto.query.ModuleVersionPageQuery;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.application.dto.vo.ModuleVersionVO;
import com.pm.application.service.IModuleService;
import com.pm.infrastructure.entity.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public PageResponse<ModuleVO> listModule(ModulePageQuery modulePageQuery) {
        return moduleService.list(modulePageQuery);
    }

    @PostMapping("/version")
    public Response addVersion(@Validated @RequestBody ModuleVersionAddCmd versionAddCmd) {
        return moduleService.addVersion(versionAddCmd);
    }

    @GetMapping("/version/list")
    public PageResponse<ModuleVersionVO> listModuleVersion(@Validated ModuleVersionPageQuery versionPageQueryCmd) {
        return moduleService.listVersion(versionPageQueryCmd);
    }

    @PutMapping("/version")
    public Response updateVersion(@Validated @RequestBody ModuleVersionUpdateCmd versionUpdateCmd) {
        return moduleService.updateVersion(versionUpdateCmd);
    }

    @DeleteMapping("/version")
    public Response deleteModelVersion(@Validated @RequestBody ModuleVersionDeleteCmd moduleVersionDeleteCmd) {
        return moduleService.deleteModuleVersion(moduleVersionDeleteCmd);
    }

    @DeleteMapping
    public Response deleteModule(@Validated @RequestBody ModuleDeleteCmd moduleDeleteCmd) {
        return moduleService.deleteModule(moduleDeleteCmd);
    }

    /**
     * 设置指定version为当前module最新的版本,更新t_module信息
     */
    @PutMapping("/latest_version")
    public Response updateModuleLatestVersion(ModuleUpdateLatestVersionCmd cmd) {

        return null;
    }
}
