package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.tool.JsonUtils;
import com.zyzh.pm.domain.project.DependModuleInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

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

    private String description;

    public DependenceDO convert2Do(ProjectDO projectDO) {
        DependenceDO dependenceDO = new DependenceDO();
        BeanUtils.copyProperties(this, dependenceDO);
        DependModuleInfo dependModuleInfo = createDependModuleInfo(projectDO);
        dependenceDO.setDependModuleInfo(JsonUtils.toJson(dependModuleInfo));
        return dependenceDO;
    }

    public DependModuleInfo createDependModuleInfo(ProjectDO projectDO) {
        DependModuleInfo dependModuleInfo = new DependModuleInfo();
        dependModuleInfo.setVersion(this.version);
        dependModuleInfo.setDescription(this.description);
        dependModuleInfo.setProjectName(projectDO.getName());
        dependModuleInfo.setModuleName(projectDO.getModuleName());
        return dependModuleInfo;
    }
}
