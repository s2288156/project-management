package com.pm.application.service.impl;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.command.ModuleAddCmdExe;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.application.service.IModuleService;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wcy
 */
@Service
public class ModuleServiceImpl implements IModuleService {
    @Autowired
    private ModuleAddCmdExe moduleAddCmdExe;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    @Override
    public SingleResponse<?> addOne(ModuleAddCmd moduleAddCmd) {
        SingleResponse<ModuleVO> moduleAddExe = moduleAddCmdExe.execute(moduleAddCmd);
        if (!moduleAddExe.isSuccess()) {
            return moduleAddExe;
        }

        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        moduleVersionDO.setMid(moduleAddExe.getData().getId());
        moduleVersionDO.setVersion(moduleAddCmd.getVersion());
        moduleVersionMapper.insert(moduleVersionDO);

        return moduleAddExe;
    }

}
