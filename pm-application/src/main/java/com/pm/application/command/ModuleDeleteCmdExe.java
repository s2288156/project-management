package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleDeleteCmd;
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
import java.util.stream.Collectors;

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
        // TODO: 2021/4/28 module dependenceProjectList为空，可以被删除。删除module的同时，对应的version也要全部删除，测试用例没有覆盖到这一点
        try {
            this.deleteModuleVersion(moduleDeleteCmd);
            moduleMapper.deleteById(moduleDeleteCmd.getId());
        } catch (Exception e) {
            log.info(">>>>>>>>>>>>>>>>>>>errorMessage:{}<<<<<<<<<<<<<<<<<<", e.toString());
        }
        return Response.buildSuccess();
    }

    private void deleteModuleVersion(ModuleDeleteCmd moduleDeleteCmd) {
        List<ModuleVersionDO> moduleVersionDOList = moduleVersionMapper.selectList(new LambdaQueryWrapper<ModuleVersionDO>()
                .eq(ModuleVersionDO::getMid, moduleDeleteCmd.getId()));
        List<String> collect = moduleVersionDOList.stream()
                .map(ModuleVersionDO::getId)
                .collect(Collectors.toList());
        moduleVersionMapper.deleteBatchIds(collect);
    }
}
