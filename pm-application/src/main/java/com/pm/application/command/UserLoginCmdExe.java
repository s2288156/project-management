package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.pm.application.dto.UserLoginCmd;
import com.pm.application.tool.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class UserLoginCmdExe {

    @Autowired
    private JwtUtil jwtUtil;

    public Response execute(UserLoginCmd userLoginCmd) {

        return Response.buildSuccess();
    }
}
