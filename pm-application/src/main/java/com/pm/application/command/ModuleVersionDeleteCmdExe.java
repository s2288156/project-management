package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleVersionDeleteCmd;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.pm.infrastructure.tool.JsonUtils;
import com.zyzh.pm.domain.project.DependModuleInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Administrator @ClassName ModuleVersionDeleteCmdExe
 * @date 2021/4/19 0019 09:58
 **/
@Component
public class ModuleVersionDeleteCmdExe {

    @Autowired
    private DependenceMapper dependenceMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    public Response execute(ModuleVersionDeleteCmd moduleVersionDeleteCmd) {
        ModuleDO moduleDO = moduleMapper.selectById(moduleVersionDeleteCmd.getMid());
        if (moduleDO.getLatestVersion().equals(moduleVersionDeleteCmd.getVersion())) {
            return Response.buildFailure(ErrorCodeEnum.LATEST_MODULE_VERSION_NOT_ALLOW_DELETE.getCode(), ErrorCodeEnum.LATEST_MODULE_VERSION_NOT_ALLOW_DELETE.getErrorMsg());
        }

        List<DependenceDO> dependenceDOList = dependenceMapper.selectList(new LambdaQueryWrapper<DependenceDO>()
                .eq(DependenceDO::getDependMid, moduleVersionDeleteCmd.getMid()));
        if (CollectionUtils.isEmpty(dependenceDOList)) {
            moduleVersionMapper.deleteById(moduleVersionDeleteCmd.getId());
            return Response.buildSuccess();
        }

        for (DependenceDO dependenceDO : dependenceDOList) {
            DependModuleInfo dependModuleInfo = JsonUtils.fromJson(dependenceDO.getDependModuleInfo(), DependModuleInfo.class);
            if (dependModuleInfo.getVersion().equals(moduleVersionDeleteCmd.getVersion())) {
                return Response.buildFailure(ErrorCodeEnum.MODULE_CITED.getCode(), ErrorCodeEnum.MODULE_CITED.getErrorMsg());
            }
        }
        moduleVersionMapper.deleteById(moduleVersionDeleteCmd.getId());
        return Response.buildSuccess();
    }
}
