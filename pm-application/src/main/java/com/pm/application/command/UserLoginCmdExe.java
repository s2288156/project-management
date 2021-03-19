package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.UserLoginCmd;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wcy
 */
@Component
public class UserLoginCmdExe {

    @Autowired
    private UserMapper userMapper;

    public Response execute(UserLoginCmd userLoginCmd) {
        Optional<UserDO> optional = userMapper.selectForUsername(userLoginCmd.getUsername());
        if (!optional.isPresent()) {
            return Response.buildFailure(ErrorCodeEnum.USERNAME_EXISTED.getErrorCode(), ErrorCodeEnum.USERNAME_EXISTED.getErrorMsg());
        }

        return Response.buildSuccess();
    }
}
