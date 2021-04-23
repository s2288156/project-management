package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleDeleteCmd;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
        List<String> projectIdList = dependenceMapper.selectDependenceInfo(moduleDeleteCmd.getId());
        if (CollectionUtils.isNotEmpty(projectIdList)) {
            return Response.buildFailure(ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode(), ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorMsg());
        }
        moduleMapper.deleteById(moduleDeleteCmd.getId());
        return Response.buildSuccess();
    }
}
