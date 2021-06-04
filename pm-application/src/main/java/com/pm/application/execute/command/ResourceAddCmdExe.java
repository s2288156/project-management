package com.pm.application.execute.command;

import com.pm.application.dto.cmd.ResourceAddCmd;
import com.pm.infrastructure.mapper.ResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class ResourceAddCmdExe {

    @Autowired
    private ResourceMapper resourceMapper;

    public String execute(ResourceAddCmd addCmd) {

        return null;
    }

}
