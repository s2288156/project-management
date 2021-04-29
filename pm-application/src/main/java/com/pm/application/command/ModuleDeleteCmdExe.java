package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.ModuleDeleteCmd;
import com.pm.infrastructure.mapper.DependenceMapper;
import com.pm.infrastructure.mapper.ModuleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

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
        if (!CollectionUtils.isEmpty(projectIdList)) {
            return Response.buildFailure(ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorCode(), ErrorCodeEnum.MODULE_DEPENDENCE_ERROR.getErrorMsg());
        }
        // TODO: 2021/4/28 module dependenceProjectList为空，可以被删除。删除module的同时，对应的version也要全部删除，测试用例没有覆盖到这一点
        moduleMapper.deleteById(moduleDeleteCmd.getId());
        return Response.buildSuccess();
    }
}
