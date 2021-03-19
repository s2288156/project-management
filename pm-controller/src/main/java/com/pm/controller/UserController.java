package com.pm.controller;

import com.alibaba.cola.dto.Response;
import com.pm.application.dto.UserLoginCmd;
import com.pm.application.dto.UserRegisterCmd;
import com.pm.application.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wcy
 */
@RequestMapping("/user")
@RestController
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping("/login")
    public Response login(@RequestBody @Validated UserLoginCmd userLoginCmd) {
        return userService.userLogin(userLoginCmd);
    }

    @PostMapping("/register")
    public Response register(@RequestBody @Validated UserRegisterCmd userRegisterCmd) {
        return userService.userRegister(userRegisterCmd);
    }
}
