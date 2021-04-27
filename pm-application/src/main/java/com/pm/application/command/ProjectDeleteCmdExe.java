package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ProjectDeleteCmd;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public Response execute(ProjectDeleteCmd cmd) {
        // 由项目id查询出依赖此项目的项目名称
        List<String> projectNameList = dependenceMapper.selectDependenceProjectByProjectId(cmd.getId());
        if (org.springframework.util.CollectionUtils.isEmpty(projectNameList)) {
            return Response.buildFailure(ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode(), ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode());
        }
        // 根据项目id查询出自己项目引用自己模块的DependenceId
        List<String> dependIdList = dependenceMapper.queryDependIdByProjectId(cmd.getId());
        if (CollectionUtils.isNotEmpty(dependIdList)) {
            //  删除项目相关的依赖
            dependenceMapper.deleteBatchIds(dependIdList);
        }
        // 删除项目相关的模块版本
        List<String> moduleVersionIdList = moduleVersionMapper.selectModuleVersionIdByProjectId(cmd.getId());
        if (CollectionUtils.isNotEmpty(moduleVersionIdList)) {
            moduleVersionMapper.deleteBatchIds(moduleVersionIdList);
        }
        // 删除项目的模块
        moduleMapper.delete(new LambdaQueryWrapper<ModuleDO>()
                .eq(ModuleDO::getPid, cmd.getId()));
        projectMapper.deleteById(cmd.getId());
        return Response.buildSuccess();
    }
}
