package com.pm.application.command;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.application.convertor.ProjectConvertor;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.mapper.ProjectMapper;
import com.zyzh.pm.domain.gateway.GroupGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wcy
 */
@Component
public class ProjectAddCmdExe {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private GroupGateway groupGateway;

    public SingleResponse<?> execute(ProjectAddCmd addCmd) {
        if (!groupGateway.existById(addCmd.getGroupId())) {
            return SingleResponse.buildFailure(ErrorCodeEnum.GROUP_NOT_FOUND.getErrorCode(), ErrorCodeEnum.GROUP_NOT_FOUND.getErrorMsg());
        }
        Optional<ProjectDO> optionalProject = projectMapper.selectByName(addCmd.getName());
        if (optionalProject.isPresent()) {
            return SingleResponse.buildFailure(ErrorCodeEnum.PROJECT_NAME_EXISTED.getErrorCode(), ErrorCodeEnum.PROJECT_NAME_EXISTED.getErrorMsg());
        }

        ProjectDO projectDO = ProjectConvertor.convertFor(addCmd);
        projectMapper.insert(projectDO);
        return SingleResponse.of(projectDO.getId());
    }

}
