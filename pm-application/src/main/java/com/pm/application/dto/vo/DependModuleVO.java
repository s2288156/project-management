package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.zyzh.pm.domain.project.DependModuleInfo;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.tool.JsonUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class DependModuleVO extends DTO {

    private String id;

    private String projectName;

    /**
     * moduleName
     */
    private String moduleName;

    /**
     * moduleVersion
     */
    private String moduleVersion;

    private String moduleDescription;

    public static DependModuleVO convertForDo(DependenceDO dependenceDO) {
        DependModuleVO dependModuleVO = new DependModuleVO();
        final String dependModuleInfoJson = dependenceDO.getDependModuleInfo();
        DependModuleInfo dependModuleInfo = JsonUtils.fromJson(dependModuleInfoJson, DependModuleInfo.class);

        dependModuleVO.setId(dependenceDO.getId());
        dependModuleVO.setModuleName(dependModuleInfo.getModuleName());
        dependModuleVO.setProjectName(dependModuleInfo.getProjectName());
        dependModuleVO.setModuleVersion(dependModuleInfo.getVersion());
        dependModuleVO.setModuleDescription(dependModuleInfo.getDescription());

        return dependModuleVO;
    }
}
