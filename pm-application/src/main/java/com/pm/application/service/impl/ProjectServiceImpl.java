package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.command.DependenceDeleteCmdExe;
import com.pm.application.command.ModuleVersionDeleteCmdExe;
import com.pm.application.command.ProjectAddCmdExe;
import com.pm.application.command.ProjectDependAddCmdExe;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.PidQuery;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.dto.cmd.ProjectDeleteCmd;
import com.pm.application.dto.cmd.ProjectDependAddCmd;
import com.pm.application.dto.cmd.ProjectPageQueryCmd;
import com.pm.application.dto.vo.DependModuleVO;
import com.pm.application.dto.vo.ProjectVO;
import com.pm.application.service.IProjectService;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ModuleDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import com.pm.infrastructure.mapper.ModuleVersionMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author wcy
 */
@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectAddCmdExe projectAddCmdExe;

    @Autowired
    private ModuleVersionDeleteCmdExe moduleVersionDeleteCmdExe;

    @Autowired
    private DependenceDeleteCmdExe dependenceDeleteCmdExe;

    @Autowired
    private ProjectDependAddCmdExe projectDependAddCmdExe;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DependenceMapper dependenceMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Override
    public SingleResponse<?> addOne(ProjectAddCmd addCmd) {
        return projectAddCmdExe.execute(addCmd);
    }

    @Override
    public PageResponse<ProjectVO> listProjects(ProjectPageQueryCmd projectPageQueryCmd) {
        Page<ProjectDO> page = projectPageQueryCmd.createPage();
        projectMapper.pageByGroupId(page, projectPageQueryCmd.getGroupId());

        List<ProjectVO> collect = page.getRecords()
                .stream()
                .map(ProjectVO::convert2DO)
                .collect(Collectors.toList());
        return PageResponse.of(collect, page.getTotal());
    }

    @Override
    public Response dependAdd(ProjectDependAddCmd dependAddCmd) {
        return projectDependAddCmdExe.execute(dependAddCmd);
    }

    @Override
    public PageResponse<DependModuleVO> listDepend(PidQuery pid) {
        Page<DependenceDO> dependPage = dependenceMapper.selectPage(pid.createPage(), new LambdaQueryWrapper<DependenceDO>()
                .eq(DependenceDO::getPid, pid.getPid()));

        List<DependModuleVO> moduleVOList = dependPage.getRecords()
                .stream()
                .map(DependModuleVO::convertForDo)
                .collect(Collectors.toList());

        return PageResponse.of(moduleVOList, dependPage.getTotal());
    }

    @Override
    public Response deleteDepend(String id) {
        dependenceMapper.deleteById(id);
        return Response.buildSuccess();
    }

    // TODO: 2021/4/26 没有事务
    @Override
    public Response deleteProject(ProjectDeleteCmd cmd) {
        // 由项目id查询出依赖此项目的项目名称
        List<String> projectNameList = dependenceMapper.selectDependenceProjectByProjectId(cmd.getId());
        // TODO: 2021/4/26 统一使用org.springframework.util.CollectionUtils
        if (CollectionUtils.isNotEmpty(projectNameList)) {
            String projectNameStr = projectNameList.stream()
                    .collect(Collectors.joining(", "));
            // TODO: 2021/4/26 不用拼接errorMsg
            return Response.buildFailure(ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode(), projectNameStr);
        }
        // TODO: 2021/4/26 project删除逻辑封装一个Exe，不用拆分
        // 删除项目相关的依赖
        dependenceDeleteCmdExe.execute(cmd.getId());
        // 删除项目相关的模块版本
//        moduleVersionDeleteCmdExe.execute(cmd.getId());
        // 删除项目的模块
        moduleMapper.delete(new LambdaQueryWrapper<ModuleDO>()
                .eq(ModuleDO::getPid, cmd.getId()));
        projectMapper.deleteById(cmd.getId());

        return Response.buildSuccess();
    }

}
