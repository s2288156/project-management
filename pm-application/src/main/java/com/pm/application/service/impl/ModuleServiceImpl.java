package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.command.ModuleAddCmdExe;
import com.pm.application.command.ModuleVersionPageQueryCmdExe;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.convertor.ModuleVersionConvertor;
import com.pm.application.dto.cmd.*;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.application.dto.vo.ModuleVersionVO;
import com.pm.application.service.IModuleService;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.pm.infrastructure.tool.JsonUtils;
import com.zyzh.exception.BizException;
import com.zyzh.pm.domain.project.DependModuleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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
    private ModuleVersionPageQueryCmdExe versionPageQueryCmdExe;

    @Autowired
    private DependenceMapper dependenceMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public SingleResponse<ModuleVO> addOne(ModuleAddCmd moduleAddCmd) {
        Optional<ModuleDO> moduleOptional = moduleMapper.selectByName(moduleAddCmd.getName());
        if (moduleOptional.isPresent()) {
            throw new BizException(ErrorCodeEnum.MODULE_NAME_EXISTED.getErrorCode(), ErrorCodeEnum.MODULE_NAME_EXISTED.getErrorMsg());
        }
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
        ModuleDO moduleDO = moduleMapper.selectById(versionAddCmd.getMid());
        if (moduleDO == null) {
            return Response.buildFailure(ErrorCodeEnum.MODULE_NOT_FOUND.getErrorCode(), ErrorCodeEnum.MODULE_NOT_FOUND.getErrorMsg());
        }

        Optional<ModuleVersionDO> versionOptional = moduleVersionMapper.selectByMidAndVersion(versionAddCmd.getMid(), versionAddCmd.getVersion());
        if (versionOptional.isPresent()) {
            return Response.buildFailure(ErrorCodeEnum.MODULE_VERSION_EXISTED.getErrorCode(), ErrorCodeEnum.MODULE_VERSION_EXISTED.getErrorMsg());
        }

        moduleVersionMapper.insert(ModuleVersionConvertor.convertFor(versionAddCmd));
        return Response.buildSuccess();
    }

    @Override
    public PageResponse<ModuleVersionVO> listVersion(ModuleVersionPageQueryCmd versionPageQueryCmd) {
        return versionPageQueryCmdExe.execute(versionPageQueryCmd);
    }

    @Override
    public Response updateVersion(ModuleVersionUpdateCmd versionUpdateCmd) {
        moduleVersionMapper.updateById(versionUpdateCmd.convert2Do());
        return Response.buildSuccess();
    }

    // TODO: 2021/4/17 代码逻辑较多，封装一个ModuleVersionDeleteCmdExe.excute进行处理
    @Override
    public Response deleteModuleVersion(ModuleVersionDeleteCmd moduleVersionDeleteCmd) {
        // TODO: 2021/4/17 1.代码格式化问题; 2.git username没有更新
        ModuleDO moduleDO=moduleMapper.selectById(moduleVersionDeleteCmd.getMid());
        if (moduleDO.getLatestVersion().equals(moduleVersionDeleteCmd.getVersion())){
            // TODO: 2021/4/17 MODULE_VERSION_NEW这个错误提示没有意义
            return Response.buildFailure(ErrorCodeEnum.MODULE_VERSION_NEW.getCode(),ErrorCodeEnum.MODULE_VERSION_NEW.getErrorMsg());
        }
        List<DependenceDO> dependenceDOList =dependenceMapper.selectList(new LambdaQueryWrapper<DependenceDO>()
                .eq(DependenceDO::getDependMid,moduleVersionDeleteCmd.getMid()));

        // TODO: 2021/4/17 没有意义的换行
        if (!CollectionUtils.isEmpty(dependenceDOList)){

            for (DependenceDO  dependenceDO:dependenceDOList) {
                DependModuleInfo dependModuleInfo = JsonUtils.fromJson(dependenceDO.getDependModuleInfo(), DependModuleInfo.class);
                if (dependModuleInfo.getVersion().equals(moduleVersionDeleteCmd.getVersion())) {
                    // TODO: 2021/4/17 for循环中return？
                    return Response.buildFailure(ErrorCodeEnum.MODULE_CITED.getCode(),dependenceDO.getPid());
                }

            }
        }
        // TODO: 2021/4/17 用deleteById进行删除操作
        moduleVersionMapper.delete(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid,moduleVersionDeleteCmd.getMid())
                .eq(ModuleVersionDO::getVersion,moduleVersionDeleteCmd.getVersion()));
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
