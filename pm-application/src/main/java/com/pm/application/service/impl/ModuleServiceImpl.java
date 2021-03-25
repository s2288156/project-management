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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author wcy
 */
@Service
public class ModuleServiceImpl implements IModuleService {
    @Autowired
    private ModuleAddCmdExe moduleAddCmdExe;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SingleResponse<ModuleVO> addOne(ModuleAddCmd moduleAddCmd) {
        SingleResponse<ModuleVO> moduleAddExe = moduleAddCmdExe.execute(moduleAddCmd);
        if (!moduleAddExe.isSuccess()) {
            return moduleAddExe;
        }

        saveModuleVersion(moduleAddCmd, moduleAddExe);
        return moduleAddExe;
    }

    private void saveModuleVersion(ModuleAddCmd moduleAddCmd, SingleResponse<ModuleVO> moduleAddExe) {
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        moduleVersionDO.setMid(moduleAddExe.getData().getId());
        moduleVersionDO.setVersion(moduleAddCmd.getVersion());
        moduleVersionMapper.insert(moduleVersionDO);
    }

}
