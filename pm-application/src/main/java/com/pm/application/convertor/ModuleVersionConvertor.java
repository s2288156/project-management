package com.pm.application.convertor;

import com.pm.application.dto.cmd.ModuleVersionAddCmd;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import org.springframework.beans.BeanUtils;

/**
 * @author wcy
 */
public class ModuleVersionConvertor {

    public static ModuleVersionDO convertFor(ModuleVersionAddCmd versionAddCmd) {
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        BeanUtils.copyProperties(versionAddCmd, moduleVersionDO);
        return moduleVersionDO;
    }
}
