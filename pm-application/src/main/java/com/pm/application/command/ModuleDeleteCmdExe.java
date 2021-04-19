package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleDeleteCmd;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class ModuleDeleteCmdExe {

    @Autowired
    private DependenceMapper dependenceMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    public Response execute(ModuleDeleteCmd moduleDeleteCmd) {
        String projectNameStr = dependenceMapper.selectDependenceInfo(moduleDeleteCmd.getId());
        if (StringUtils.isNoneEmpty(projectNameStr)) {
            return Response.buildFailure(ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode(), projectNameStr);
        }
        moduleMapper.deleteById(moduleDeleteCmd.getId());
        return Response.buildSuccess();
    }
}
