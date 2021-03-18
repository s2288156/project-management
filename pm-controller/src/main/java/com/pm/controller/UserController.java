package com.pm.controller;

import com.alibaba.cola.dto.Response;
import com.pm.application.dto.UserLoginCmd;
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

    @PostMapping("/login")
    public Response login(@RequestBody @Validated UserLoginCmd userLoginCmd) {

        return Response.buildSuccess();
    }
}
