package com.pm.application.execute.command;

import com.pm.application.dto.cmd.RoleSetResourcesCmd;
import com.pm.infrastructure.mapper.RoleResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author wcy
 */
@Component
public class RoleSetResourcesCmdExe {

    @Autowired
    private RoleResourceMapper roleResourceMapper;

    @Transactional(rollbackFor = Exception.class)
    public void execute(RoleSetResourcesCmd setResourcesCmd) {
        roleResourceMapper.deleteByRoleId(setResourcesCmd.getRoleId());
        roleResourceMapper.insertBatchResourceIds(setResourcesCmd.convert2DoList());
    }

}
