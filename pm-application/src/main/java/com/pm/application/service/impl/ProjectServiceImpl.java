package com.pm.application.service.impl;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.convertor.ProjectConvertor;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.service.IProjectService;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.mapper.ProjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wcy
 */
@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public SingleResponse<?> addOne(ProjectAddCmd addCmd) {
        ProjectDO projectDO = ProjectConvertor.convertFor(addCmd);
        projectMapper.insert(projectDO);
        return SingleResponse.buildSuccess();
    }
}
