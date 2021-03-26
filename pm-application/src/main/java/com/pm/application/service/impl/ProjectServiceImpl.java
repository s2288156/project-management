package com.pm.application.service.impl;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pm.application.command.ProjectAddCmdExe;
import com.pm.application.dto.GroupId;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.dto.vo.ProjectVO;
import com.pm.application.service.IProjectService;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.entity.PageResponse;
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
    private ProjectMapper projectMapper;

    @Override
    public SingleResponse<?> addOne(ProjectAddCmd addCmd) {
        return projectAddCmdExe.execute(addCmd);
    }

    @Override
    public PageResponse<ProjectVO> listProjects(GroupId groupId) {
        Page<ProjectDO> page = groupId.createPage();

        projectMapper.pageBy(page, groupId.getGroupId());

        List<ProjectVO> collect = page.getRecords()
                .stream()
                .map(ProjectVO::convert2DO)
                .collect(Collectors.toList());
        return PageResponse.of(collect, page.getTotal());
    }

}
