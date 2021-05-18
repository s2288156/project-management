package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.convertor.ModuleConvertor;
import com.pm.application.convertor.ModuleVersionConvertor;
import com.pm.application.dto.cmd.ModuleAddCmd;
import com.pm.application.dto.cmd.ModuleDeleteCmd;
import com.pm.application.dto.cmd.ModuleUpdateLatestVersionCmd;
import com.pm.application.dto.cmd.ModuleVersionAddCmd;
import com.pm.application.dto.cmd.ModuleVersionDeleteCmd;
import com.pm.application.dto.cmd.ModuleVersionUpdateCmd;
import com.pm.application.dto.query.ModulePageQuery;
import com.pm.application.dto.query.ModuleVersionPageQuery;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.application.dto.vo.ModuleVersionVO;
import com.pm.application.execute.command.ModuleAddCmdExe;
import com.pm.application.execute.command.ModuleDeleteCmdExe;
import com.pm.application.execute.command.ModuleVersionDeleteCmdExe;
import com.pm.application.execute.query.ModuleVersionPageQueryExe;
import com.pm.application.service.IModuleService;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.zyzh.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
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

    @Autowired
    private ModuleVersionPageQueryExe versionPageQueryCmdExe;

    @Autowired
    private ModuleVersionDeleteCmdExe moduleVersionDeleteCmdExe;

    @Autowired
    private ModuleDeleteCmdExe moduleDeleteCmdExe;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SingleResponse<ModuleVO> addOne(ModuleAddCmd moduleAddCmd) {
        Optional<ModuleDO> moduleOptional = moduleMapper.selectByName(moduleAddCmd.getName());
        if (moduleOptional.isPresent()) {
            throw new BizException(ErrorCodeEnum.MODULE_NAME_EXISTED);
        }
        SingleResponse<ModuleVO> moduleAddExe = moduleAddCmdExe.execute(moduleAddCmd);
        if (!moduleAddExe.isSuccess()) {
            return moduleAddExe;
        }
        saveModuleVersion(moduleAddCmd, moduleAddExe);
        return moduleAddExe;
    }

    @Override
    public PageResponse<ModuleVO> list(ModulePageQuery pageQueryCmd) {
        Page<ModuleDO> moduleDoPage = moduleMapper.listProjectAndVersion(pageQueryCmd.createPage(), pageQueryCmd.getPid());
        List<ModuleVO> moduleVos = moduleDoPage.getRecords()
                .stream()
                .map(ModuleVO::convertForDo)
                .collect(Collectors.toList());
        return PageResponse.of(moduleVos, moduleDoPage.getTotal());
    }

    @Override
    public Response addVersion(ModuleVersionAddCmd versionAddCmd) {
        ModuleDO moduleDO = moduleMapper.selectById(versionAddCmd.getMid());
        if (moduleDO == null) {
            throw new BizException(ErrorCodeEnum.MODULE_NOT_FOUND);
        }

        Optional<ModuleVersionDO> versionOptional = moduleVersionMapper.selectByMidAndVersion(versionAddCmd.getMid(), versionAddCmd.getVersion());
        if (versionOptional.isPresent()) {
            throw new BizException(ErrorCodeEnum.MODULE_VERSION_EXISTED);
        }

        moduleVersionMapper.insert(ModuleVersionConvertor.convertFor(versionAddCmd));
        return Response.buildSuccess();
    }

    @Override
    public PageResponse<ModuleVersionVO> listVersion(ModuleVersionPageQuery versionPageQueryCmd) {
        return versionPageQueryCmdExe.execute(versionPageQueryCmd);
    }

    @Override
    public Response updateVersion(ModuleVersionUpdateCmd versionUpdateCmd) {
        moduleVersionMapper.updateById(versionUpdateCmd.convert2Do());
        return Response.buildSuccess();
    }

    @Override
    public Response deleteModuleVersion(ModuleVersionDeleteCmd moduleVersionDeleteCmd) {
        return moduleVersionDeleteCmdExe.execute(moduleVersionDeleteCmd);
    }

    @Override
    public Response deleteModule(ModuleDeleteCmd moduleDeleteCmd) {
        return moduleDeleteCmdExe.execute(moduleDeleteCmd);
    }

    @Override
    public Response moduleUpdateLatestVersion(ModuleUpdateLatestVersionCmd cmd) {
        ModuleDO moduleDO = ModuleConvertor.INSTANCE.convert2Do(cmd);
        moduleMapper.updateById(moduleDO);
        return Response.buildSuccess();
    }

    private void saveModuleVersion(ModuleAddCmd moduleAddCmd, SingleResponse<ModuleVO> moduleAddExe) {
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        moduleVersionDO.setMid(moduleAddExe.getData().getId());
        moduleVersionDO.setVersion(moduleAddCmd.getVersion());
        moduleVersionDO.setDescription(moduleAddCmd.getDescription());
        moduleVersionMapper.insert(moduleVersionDO);
    }

}
