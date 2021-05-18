package com.pm.application.execute.command;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.convertor.ProjectConvertor;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.mapper.ProjectMapper;
import com.zyzh.exception.BizException;
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
            throw new BizException(ErrorCodeEnum.GROUP_NOT_FOUND);
        }
        Optional<ProjectDO> optionalProject = projectMapper.selectByName(addCmd.getName());
        if (optionalProject.isPresent()) {
            throw new BizException(ErrorCodeEnum.PROJECT_NAME_EXISTED);
        }

        ProjectDO projectDO = ProjectConvertor.convertFor(addCmd);
        projectMapper.insert(projectDO);
        return SingleResponse.of(projectDO.getId());
    }

}
