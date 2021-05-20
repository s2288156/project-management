package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.convertor.ProjectConvertor;
import com.pm.application.execute.command.ProjectAddCmdExe;
import com.pm.application.execute.command.ProjectDeleteCmdExe;
import com.pm.application.execute.command.ProjectDependAddCmdExe;
import com.pm.application.dto.PidQuery;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.dto.cmd.ProjectDeleteCmd;
import com.pm.application.dto.cmd.ProjectDependAddCmd;
import com.pm.application.dto.query.ProjectPageQuery;
import com.pm.application.dto.vo.DependModuleVO;
import com.pm.application.dto.vo.ProjectVO;
import com.pm.application.service.IProjectService;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.entity.PageResponse;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private ProjectDeleteCmdExe projectDeleteCmdExe;

    @Autowired
    private ProjectDependAddCmdExe projectDependAddCmdExe;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private DependenceMapper dependenceMapper;

    @Override
    public SingleResponse<?> addOne(ProjectAddCmd addCmd) {
        return projectAddCmdExe.execute(addCmd);
    }

    @Override
    public PageResponse<ProjectVO> listProjects(ProjectPageQuery projectPageQuery) {
        Page<ProjectDO> page = projectPageQuery.createPage();
        projectMapper.pageByGroupId(page, projectPageQuery.getGroupId());

        List<ProjectVO> collect = page.getRecords()
                .stream()
                .map(ProjectConvertor.INSTANCE::convertDo2ProjectVo)
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

    @Override
    public Response deleteProject(ProjectDeleteCmd cmd) {
        return projectDeleteCmdExe.execute(cmd);
    }

}
