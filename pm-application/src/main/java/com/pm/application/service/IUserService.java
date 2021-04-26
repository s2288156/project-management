package com.pm.application.service;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.UserLoginCmd;
import com.pm.application.dto.cmd.UserRegisterCmd;
import com.pm.application.dto.vo.UserVO;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;

/**
 * @author wcy
 */
public interface IUserService {

    Response userRegister(UserRegisterCmd userRegisterCmd);

    SingleResponse userLogin(UserLoginCmd loginCmd);

    PageResponse<UserVO> listUser(PageQuery pageQuery);
}
