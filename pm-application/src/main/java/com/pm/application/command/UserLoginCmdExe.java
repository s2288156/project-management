package com.pm.application.command;

import com.alibaba.cola.dto.Response;
import com.pm.application.consts.ErrorCodeEnum;
import com.pm.application.dto.UserLoginCmd;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author wcy
 */
@Component
public class UserLoginCmdExe {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Response execute(UserLoginCmd userLoginCmd) {
        Optional<UserDO> optional = userMapper.selectForUsername(userLoginCmd.getUsername());
        if (!optional.isPresent()) {
            return Response.buildFailure(ErrorCodeEnum.USERNAME_NOT_FOUND.getErrorCode(), ErrorCodeEnum.USERNAME_NOT_FOUND.getErrorMsg());
        }
        if (!validPwd(userLoginCmd.getPassword(), optional.get().getPassword())) {
            return Response.buildFailure(ErrorCodeEnum.PASSWORD_FAIL.getErrorCode(), ErrorCodeEnum.PASSWORD_FAIL.getErrorMsg());
        }
        return Response.buildSuccess();
    }

    private boolean validPwd(String inputPwd, String dbPwd) {
        return passwordEncoder.matches(inputPwd, dbPwd);
    }
}
