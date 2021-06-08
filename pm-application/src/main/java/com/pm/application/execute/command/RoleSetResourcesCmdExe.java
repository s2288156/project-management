package com.pm.application.execute.command;

import com.pm.application.dto.cmd.RoleSetResourcesCmd;
import com.pm.infrastructure.dataobject.RoleResourceDO;
import com.pm.infrastructure.mapper.RoleResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
        List<RoleResourceDO> resourceList = setResourcesCmd.convert2DoList();
        if (!CollectionUtils.isEmpty(resourceList)) {
            roleResourceMapper.insertBatchResourceIds(resourceList);
        }
    }

}
