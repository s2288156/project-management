package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.command.ModuleAddCmdExe;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.cmd.ModulePageQueryCmd;
import com.pm.application.dto.cmd.ModuleVersionAddCmd;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.application.service.IModuleService;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@Service
public class ModuleServiceImpl implements IModuleService {
    @Autowired
    private ModuleAddCmdExe moduleAddCmdExe;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    @Autowired
    private ModuleMapper moduleMapper;

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

    @Override
    public PageResponse<ModuleVO> list(ModulePageQueryCmd pageQueryCmd) {
        Page<ModuleDO> moduleDoPage = moduleMapper.listProjectAndVersion(pageQueryCmd.createPage(), pageQueryCmd.getPid());
        List<ModuleVO> moduleVos = moduleDoPage.getRecords()
                .stream()
                .map(ModuleVO::convertForDo)
                .collect(Collectors.toList());
        return PageResponse.of(moduleVos, moduleDoPage.getTotal());
    }

    @Override
    public Response addVersion(ModuleVersionAddCmd versionAddCmd) {

        return null;
    }

    private void saveModuleVersion(ModuleAddCmd moduleAddCmd, SingleResponse<ModuleVO> moduleAddExe) {
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        moduleVersionDO.setMid(moduleAddExe.getData().getId());
        moduleVersionDO.setVersion(moduleAddCmd.getVersion());
        moduleVersionMapper.insert(moduleVersionDO);
    }

}
