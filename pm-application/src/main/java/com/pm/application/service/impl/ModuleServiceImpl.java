package com.pm.application.service.impl;

import com.pm.application.command.ModuleAddCmdExe;
import com.pm.application.service.IModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wcy
 */
@Service
public class ModuleServiceImpl implements IModuleService {
    @Autowired
    private ModuleAddCmdExe moduleAddCmdExe;
}
