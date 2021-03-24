package com.pm.application.service.impl;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.command.ProjectAddCmdExe;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wcy
 */
@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectAddCmdExe projectAddCmdExe;

    @Override
    public SingleResponse<?> addOne(ProjectAddCmd addCmd) {
        return projectAddCmdExe.execute(addCmd);
    }
}
