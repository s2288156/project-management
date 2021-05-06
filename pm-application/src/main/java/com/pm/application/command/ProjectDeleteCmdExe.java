package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleDeleteCmd;
import com.pm.application.dto.cmd.ProjectDeleteCmd;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
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
public class ProjectDeleteCmdExe {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DependenceMapper dependenceMapper;

    @Autowired
    private ModuleDeleteCmdExe moduleDeleteCmdExe;


    /**
     * 删除项目：
     * 1. 判断项目中的module是否有被依赖，如果有则不允许删除（query）
     * 2. 没有被依赖，删除包含的全部module，已经moduleVersion（delete）
     * 3. 删除全部的依赖（delete）
     * 4. 删除项目（delete）
     *
     * @param cmd
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Response execute(ProjectDeleteCmd cmd) {

        List<DependenceDO> dependenceList = dependenceMapper.queryDependenceByProjectId(cmd.getId());
        if (!CollectionUtils.isEmpty(dependenceList)) {
            return Response.buildFailure(ErrorCodeEnum.PROJECT_MODULE_DEPENDENCE_ERROR.getErrorCode(), ErrorCodeEnum.PROJECT_MODULE_DEPENDENCE_ERROR.getErrorMsg());
        }
        deleteModuleAndModuleVersion(cmd);
        deleteDependence(cmd);
        projectMapper.deleteById(cmd.getId());
        return Response.buildSuccess();
    }

    private void deleteDependence(ProjectDeleteCmd cmd) {
        dependenceMapper.delete(new LambdaQueryWrapper<DependenceDO>()
                .eq(DependenceDO::getPid, cmd.getId()));
    }

    private void deleteModuleAndModuleVersion(ProjectDeleteCmd cmd) {
        List<ModuleDO> moduleDOList = moduleMapper.selectList(new LambdaQueryWrapper<ModuleDO>().eq(ModuleDO::getPid, cmd.getId()));
        for (ModuleDO moduleDO : moduleDOList) {
            ModuleDeleteCmd moduleDeleteCmd = new ModuleDeleteCmd();
            moduleDeleteCmd.setId(moduleDO.getId());
            moduleDeleteCmdExe.execute(moduleDeleteCmd);
        }
    }

}
