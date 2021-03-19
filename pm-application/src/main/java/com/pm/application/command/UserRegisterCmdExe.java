package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.cmd.UserRegisterCmd;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.UserMapper;
import com.zyzh.pm.domain.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author wcy
 */
@Component
public class UserRegisterCmdExe {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserGateway userGateway;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Response execute(UserRegisterCmd userRegisterCmd) {
        if (userGateway.existForUsername(userRegisterCmd.getUsername())) {
            return Response.buildFailure(ErrorCodeEnum.USERNAME_EXISTED.getErrorCode(), ErrorCodeEnum.USERNAME_EXISTED.getErrorMsg());
        }
        UserDO userDO = encoderPwd(userRegisterCmd);
        userMapper.insert(userDO);
        return Response.buildSuccess();
    }

    private UserDO encoderPwd(UserRegisterCmd cmd) {
        UserDO userDO = new UserDO();
        userDO.setUsername(cmd.getUsername());
        userDO.setPassword(passwordEncoder.encode(cmd.getPassword()));
        return userDO;
    }
}
