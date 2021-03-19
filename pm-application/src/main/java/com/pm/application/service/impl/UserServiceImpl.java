package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.pm.application.command.UserLoginCmdExe;
import com.pm.application.command.UserRegisterCmdExe;
import com.pm.application.dto.UserLoginCmd;
import com.pm.application.dto.UserRegisterCmd;
import com.pm.application.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wcy
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRegisterCmdExe userRegisterCmdExe;

    @Autowired
    private UserLoginCmdExe userLoginCmdExe;

    @Override
    public Response userRegister(UserRegisterCmd userRegisterCmd) {
        return userRegisterCmdExe.execute(userRegisterCmd);
    }

    @Override
    public Response userLogin(UserLoginCmd loginCmd) {
        return userLoginCmdExe.execute(loginCmd);
    }
}
