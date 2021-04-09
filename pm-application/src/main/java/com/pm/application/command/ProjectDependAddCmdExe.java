package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.convertor.ProjectConvertor;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.dto.cmd.ProjectDependAddCmd;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.mapper.DependenceMapper;
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
public class ProjectDependAddCmdExe {

    @Autowired
    private DependenceMapper dependenceMapper;

    @Autowired
    private ProjectMapper projectMapper;

    public Response execute(ProjectDependAddCmd dependAddCmd) {
        Optional<DependenceDO> doOptional = dependenceMapper.selectByPidAndDependMid(dependAddCmd.getPid(), dependAddCmd.getDependMid());
        if (doOptional.isPresent()) {
            return Response.buildFailure(ErrorCodeEnum.RE_DEPEND_NOT_ALLOW.getErrorCode(), ErrorCodeEnum.RE_DEPEND_NOT_ALLOW.getErrorMsg());
        }
        Optional<ProjectDO> projectOptional = projectMapper.selectByMid(dependAddCmd.getDependMid());
        ProjectDO projectDO = projectOptional.orElseThrow(() -> new BizException(ErrorCodeEnum.PROJECT_NOT_FOUND));
        dependenceMapper.insert(dependAddCmd.convert2Do(projectDO));
        return Response.buildSuccess();
    }

}
