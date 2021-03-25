package com.pm.controller;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
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
}
