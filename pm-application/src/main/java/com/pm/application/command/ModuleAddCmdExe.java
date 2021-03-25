package com.pm.application.command;

import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.ModuleAddCmd;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class ModuleAddCmdExe {

    public SingleResponse<?> execute(ModuleAddCmd addCmd) {

        return SingleResponse.buildSuccess();
    }
}
