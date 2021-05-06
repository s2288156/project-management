package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.application.dto.cmd.ModuleDeleteCmd;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ModuleVersionDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wcy
 */
@Slf4j
@Component
public class ModuleDeleteCmdExe {

    @Autowired
    private DependenceMapper dependenceMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(ModuleDeleteCmd moduleDeleteCmd) {
        List<DependenceDO> dependenceDOList = dependenceMapper.selectList(new LambdaQueryWrapper<DependenceDO>()
                .eq(DependenceDO::getDependMid, moduleDeleteCmd.getId()));
        if (!CollectionUtils.isEmpty(dependenceDOList)) {
            return Response.buildFailure(ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode(), ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorMsg());
        }
        deleteModuleVersion(moduleDeleteCmd);
        moduleMapper.deleteById(moduleDeleteCmd.getId());
        return Response.buildSuccess();
    }

    private void deleteModuleVersion(ModuleDeleteCmd moduleDeleteCmd) {
        moduleVersionMapper.delete(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid, moduleDeleteCmd.getId()));
    }
}
