package com.pm.application.convertor;

import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.infrastructure.dataobject.ProjectDO;

/**
 * @author wcy
 */
public class ProjectConvertor {

    public static ProjectDO convertFor(ProjectAddCmd addCmd) {
        ProjectDO projectDO = new ProjectDO();
        projectDO.setGroupId(addCmd.getGroupId());
        projectDO.setName(addCmd.getName());
        projectDO.setDescription(addCmd.getDescription());
        return projectDO;
    }
}
