package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ProjectDeleteCmd;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author Administrator
 * @version 1.0
 * @description: TODO
 * @date 2021/4/26 16:23
 */
@Component
public class ProjectDeleteCmdExe {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ModuleVersionMapper moduleVersionMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DependenceMapper dependenceMapper;

    // TODO: 2021/4/28 逻辑不清晰，sql复杂 
    /**
     * 删除项目：
     * 1. 判断项目中的module是否有被依赖，如果有则不允许删除（query）
     * 2. 没有被依赖，删除包含的全部module，已经moduleVersion（delete）
     * 3. 删除全部的依赖（delete）
     * 4. 删除项目（delete）
     * @param cmd
     * @return
     */
    public Response execute(ProjectDeleteCmd cmd) {
        // 由项目id查询出依赖此项目的项目名称
        List<String> projectNameList = dependenceMapper.selectDependenceProjectByProjectId(cmd.getId());
        if (!CollectionUtils.isEmpty(projectNameList)) {
            return Response.buildFailure(ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode(), ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode());
        }
        // 根据项目id查询出自己项目引用自己模块的DependenceId
        List<String> dependIdList = dependenceMapper.queryDependIdByProjectId(cmd.getId());
        if (!CollectionUtils.isEmpty(dependIdList)) {
            //  删除项目相关的依赖
            dependenceMapper.deleteBatchIds(dependIdList);
        }
        // 删除项目相关的模块版本
        List<String> moduleVersionIdList = moduleVersionMapper.selectModuleVersionIdByProjectId(cmd.getId());
        if (!CollectionUtils.isEmpty(moduleVersionIdList)) {
            moduleVersionMapper.deleteBatchIds(moduleVersionIdList);
        }
        // 删除项目的模块
        moduleMapper.delete(new LambdaQueryWrapper<ModuleDO>()
                .eq(ModuleDO::getPid, cmd.getId()));
        projectMapper.deleteById(cmd.getId());
        return Response.buildSuccess();
    }
}
