package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import com.pm.application.convertor.DependenceConvertor;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.tool.JsonUtils;
import com.pm.domain.project.DependModuleInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ProjectDependAddCmd extends Command {

    @NotBlank
    private String pid;

    @NotBlank
    private String dependMid;

    @NotBlank
    private String version;

    public DependenceDO convert2Do(ProjectDO projectDO) {
        DependenceDO dependenceDO = DependenceConvertor.INSTANCE.convert2DO(this);
        DependModuleInfo dependModuleInfo = createDependModuleInfo(projectDO);
        dependenceDO.setDependModuleInfo(JsonUtils.toJson(dependModuleInfo));
        return dependenceDO;
    }

    public DependModuleInfo createDependModuleInfo(ProjectDO projectDO) {
        DependModuleInfo dependModuleInfo = new DependModuleInfo();
        dependModuleInfo.setVersion(this.version);
        dependModuleInfo.setProjectName(projectDO.getName());
        dependModuleInfo.setModuleName(projectDO.getModuleName());
        return dependModuleInfo;
    }
}
