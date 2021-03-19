package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.pm.application.dto.UserLoginCmd;
import com.pm.application.dto.UserRegisterCmd;

/**
 * @author wcy
 */
public interface IUserService {

    Response userRegister(UserRegisterCmd userRegisterCmd);

    Response userLogin(UserLoginCmd loginCmd);
}
