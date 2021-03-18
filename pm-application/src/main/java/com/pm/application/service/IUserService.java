package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.pm.application.dto.UserRegisterCmd;

/**
 * @author wcy
 */
public interface IUserService {

    Response userRegister(UserRegisterCmd userRegisterCmd);
}
