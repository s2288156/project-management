package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.command.ModuleAddCmdExe;
import com.pm.application.command.ModuleVersionPageQueryCmdExe;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.convertor.ModuleVersionConvertor;
import com.pm.application.dto.cmd.*;
import com.pm.application.dto.vo.ModuleVO;
import com.pm.application.dto.vo.ModuleVersionVO;
import com.pm.application.service.IModuleService;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
import com.zyzh.exception.BizException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.management.ObjectName;
import javax.validation.constraints.NotBlank;
import java.sql.SQLOutput;
import java.util.*;
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
    private ProjectMapper projectMapper;

    @Autowired
    private ModuleVersionPageQueryCmdExe versionPageQueryCmdExe;

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

    // TODO: 2021/4/16 删除步骤较多，单独封装一个CmdExe类处理，避免此service中代码过多
    @Override
    // TODO: 2021/4/16 加事务的意义是什么？
    @Transactional(rollbackFor = Exception.class)
    public Response deleteModule(String id) {
        // 1.查询模块id是否在t_moudle中存在
        ModuleDO moduleDO = moduleMapper.selectById(id);
        if (moduleDO == null) {
            return Response.buildFailure(ErrorCodeEnum.MODULE_NOT_FOUND.getErrorCode(), ErrorCodeEnum.MODULE_NOT_FOUND.getErrorMsg());
        }
        // 2.判断该模块id是否被其他项目依赖
        List<String> pidList = moduleMapper.selectDependenceByMid(id);
        // TODO: 2021/4/16 用工具类判断 CollectionUtils.isEmpty()
        if (pidList != null && !pidList.isEmpty()) {
            Set<String> pidSet = pidList.stream().map(Object::toString).collect(Collectors.toCollection(TreeSet::new));
            // TODO: 2021/4/19 不要在for循环中查询
            String projectNameStr = pidSet.stream().map(s -> projectMapper.selectById(s)).map(ProjectDO::getName).collect(Collectors.joining(","));
            return Response.buildFailure(ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode(), projectNameStr);
        } else {
            // 3.如果未被依赖 直接删除
            moduleMapper.deleteById(id);
            return Response.buildSuccess();
        }
    }

    private void saveModuleVersion(ModuleAddCmd moduleAddCmd, SingleResponse<ModuleVO> moduleAddExe) {
        ModuleVersionDO moduleVersionDO = new ModuleVersionDO();
        moduleVersionDO.setMid(moduleAddExe.getData().getId());
        moduleVersionDO.setVersion(moduleAddCmd.getVersion());
        moduleVersionDO.setDescription(moduleAddCmd.getDescription());
        moduleVersionMapper.insert(moduleVersionDO);
    }

}
