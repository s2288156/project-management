package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.pm.application.convertor.UserConvertor;
import com.pm.application.dto.UserLoginCmd;
import com.pm.infrastructure.tool.JwtUtil;
import com.zyzh.pm.domain.gateway.UserGateway;
import com.zyzh.pm.domain.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class UserLoginCmdExe {

    @Autowired
    private UserGateway userGateway;

    public Response execute(UserLoginCmd userLoginCmd) {
        User user = UserConvertor.toEntity(userLoginCmd);


        return Response.buildSuccess();
    }
}
