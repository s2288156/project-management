package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.pm.infrastructure.dataobject.ModuleDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ModuleVO extends DTO {

    private String id;

    private String projectName;

    /**
     * moduleName
     */
    private String name;

    /**
     * moduleVersion
     */
    private String version;

    public static ModuleVO createForId(String id) {
        ModuleVO moduleVO = new ModuleVO();
        moduleVO.setId(id);
        return moduleVO;
    }

    public static ModuleVO convertForDo(ModuleDO moduleDO) {
        ModuleVO moduleVO = new ModuleVO();
        BeanUtils.copyProperties(moduleDO, moduleVO);
        return moduleVO;
    }
}
