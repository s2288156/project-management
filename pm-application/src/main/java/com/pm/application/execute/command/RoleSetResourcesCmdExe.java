package com.pm.application.execute.command;

import com.pm.application.dto.cmd.RoleSetResourcesCmd;
import com.pm.infrastructure.mapper.RoleResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class RoleSetResourcesCmdExe {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    public void execute(RoleSetResourcesCmd setResourcesCmd) {
        
        roleResourceMapper.insertBatchResourceIds(setResourcesCmd.convert2DoList());
    }

}
