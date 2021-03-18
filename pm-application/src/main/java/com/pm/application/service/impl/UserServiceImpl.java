package com.pm.application.service.impl;

import com.alibaba.cola.dto.Response;
import com.pm.application.command.UserRegisterCmdExe;
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

    @Override
    public Response userRegister(UserRegisterCmd userRegisterCmd) {
        return userRegisterCmdExe.execute(userRegisterCmd);
    }
}
