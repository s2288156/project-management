package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pm.application.dto.cmd.ProjectDeleteCmd;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.DependenceDO;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.GroupMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author wcy
 */
@Component
public class GroupDeleteExe {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private ProjectDeleteCmdExe projectDeleteCmdExe;

    @Transactional(rollbackFor = Exception.class)
    public Response execute(String id) {
        List<String> groupAllPid = groupMapper.listAllPidByGroupId(id);
        if (!CollectionUtils.isEmpty(groupAllPid)) {
            groupAllPid.forEach(pid -> {
                ProjectDeleteCmd projectDeleteCmd = new ProjectDeleteCmd();
                projectDeleteCmd.setId(pid);
                projectDeleteCmdExe.execute(projectDeleteCmd);
            });
        }
        groupMapper.deleteById(id);
        return Response.buildSuccess();
    }
}
