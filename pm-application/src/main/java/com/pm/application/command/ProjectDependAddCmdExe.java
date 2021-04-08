package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.convertor.ProjectConvertor;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.dto.cmd.ProjectDependAddCmd;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ProjectMapper;
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

    public Response execute(ProjectDependAddCmd dependAddCmd) {
        dependenceMapper.insert(dependAddCmd.convert2Do());
        return Response.buildSuccess();
    }

}
