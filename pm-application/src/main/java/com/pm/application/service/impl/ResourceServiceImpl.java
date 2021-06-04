package com.pm.application.service.impl;

import com.pm.application.dto.cmd.ResourceAddCmd;
import com.pm.application.execute.command.ResourceAddCmdExe;
import com.pm.application.service.IResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wcy
 */
@Service
public class ResourceServiceImpl implements IResourceService {
    @Autowired
    private ResourceAddCmdExe resourceAddCmdExe;

    @Override
    public String addResource(ResourceAddCmd resourceAddCmd) {
        return resourceAddCmdExe.execute(resourceAddCmd);
    }

}
