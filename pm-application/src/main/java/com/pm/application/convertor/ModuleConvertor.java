package com.pm.application.convertor;

import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.infrastructure.dataobject.ModuleDO;
import org.springframework.beans.BeanUtils;

/**
 * @author wcy
 */
public class ModuleConvertor {

    public static ModuleDO convert2Do(ModuleAddCmd moduleAddCmd) {
        ModuleDO moduleDO = new ModuleDO();
        BeanUtils.copyProperties(moduleAddCmd, moduleDO);
        moduleDO.setLatestVersion(moduleAddCmd.getVersion());
        return moduleDO;
    }
}
