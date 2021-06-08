package com.pm.controller.sys;

import com.alibaba.cola.dto.Response;
import com.alibaba.cola.dto.SingleResponse;
import com.pm.application.dto.cmd.UserLoginCmd;
import com.pm.application.dto.cmd.UserRegisterCmd;
import com.pm.application.dto.cmd.UserSetRolesCmd;
import com.pm.application.dto.vo.LoginUserVO;
import com.pm.application.dto.vo.UserDetailVO;
import com.pm.application.dto.vo.UserVO;
import com.pm.application.service.IUserService;
import com.pm.infrastructure.entity.PageQuery;
import com.pm.infrastructure.entity.PageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wcy
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public SingleResponse<LoginUserVO> login(@RequestBody @Validated UserLoginCmd userLoginCmd) {
        return userService.userLogin(userLoginCmd);
    }

    @PostMapping("/register")
    public Response register(@RequestBody @Validated UserRegisterCmd userRegisterCmd) {
        return userService.userRegister(userRegisterCmd);
    }

    @GetMapping("/info")
    public SingleResponse<UserDetailVO> info(String token) {
        return SingleResponse.of(UserDetailVO.createForToken(token));
    }

    @GetMapping("/list")
    public PageResponse<UserVO> list(PageQuery pageQuery) {
        return userService.listUser(pageQuery);
    }

    @PostMapping("/set_roles")
    public Response setRoles(@Validated @RequestBody UserSetRolesCmd userSetRolesCmd) {
        return userService.userSetRoles(userSetRolesCmd);
    }
}
