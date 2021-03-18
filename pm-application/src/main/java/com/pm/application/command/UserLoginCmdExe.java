package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.pm.application.dto.UserLoginCmd;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class UserLoginCmdExe {

    public Response execute(UserLoginCmd userLoginCmd) {

        return Response.buildSuccess();
    }
}
