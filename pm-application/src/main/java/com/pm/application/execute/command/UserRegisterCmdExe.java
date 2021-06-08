package com.pm.application.execute.command;

import com.alibaba.cola.dto.Response;
import com.pm.application.dto.cmd.UserRegisterCmd;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.mapper.UserMapper;
import com.zyzh.exception.BizException;
import com.pm.domain.consts.BizConstants;
import com.pm.domain.gateway.UserGateway;
import org.apache.commons.lang3.StringUtils;
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
        confirmPasswordCheck(userRegisterCmd.getPassword(), userRegisterCmd.getConfirmPassword());

        usernameExistedCheck(userRegisterCmd.getUsername());
        UserDO userDO = encoderPwd(userRegisterCmd);
        userMapper.insert(userDO);
        return Response.buildSuccess();
    }

    /**
     * 检查用户名是否重复
     */
    private void usernameExistedCheck(String username) {
        if (userGateway.existForUsername(username)) {
            throw new BizException(ErrorCodeEnum.USERNAME_EXISTED);
        }
    }

    /**
     * 如果两次输入的密码不相同，则返回错误
     */
    private void confirmPasswordCheck(String password, String confirmPassword) {
        if (!StringUtils.equals(password, confirmPassword)) {
            throw new BizException(ErrorCodeEnum.TWO_PASSWORD_ENTERED_NOT_SAME);
        }
    }

    private UserDO encoderPwd(UserRegisterCmd cmd) {
        UserDO userDO = new UserDO();
        userDO.setUsername(cmd.getUsername());
        userDO.setPassword(passwordEncoder.encode(cmd.getPassword()));
        userDO.setName(cmd.getName());
        userDO.setIcon(BizConstants.DEFAULT_AVATAR);
        return userDO;
    }
}
