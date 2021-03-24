package com.pm.application.service.impl;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.BizException;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.convertor.ProjectConvertor;
import com.pm.application.dto.cmd.ProjectAddCmd;
import com.pm.application.service.IProjectService;
import com.pm.infrastructure.dataobject.ProjectDO;
import com.pm.infrastructure.mapper.ProjectMapper;
import com.zyzh.pm.domain.gateway.GroupGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wcy
 */
@Service
public class ProjectServiceImpl implements IProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private GroupGateway groupGateway;

    @Override
    public SingleResponse<?> addOne(ProjectAddCmd addCmd) {
        if (!groupGateway.existById(addCmd.getGroupId())) {
            throw new BizException(ErrorCodeEnum.GROUP_NOT_FOUND.getErrorCode(), ErrorCodeEnum.GROUP_NOT_FOUND.getErrorMsg());
        }
        ProjectDO projectDO = ProjectConvertor.convertFor(addCmd);
        projectMapper.insert(projectDO);
        return SingleResponse.buildSuccess();
    }
}
