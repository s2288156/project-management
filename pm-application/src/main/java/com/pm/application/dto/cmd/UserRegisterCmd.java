package com.pm.application.dto.cmd;

import com.alibaba.cola.dto.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegisterCmd extends Command {

    @NotBlank(message = "用户名必传")
    private String username;

    /**
     * 姓名
     */
    private String name;

    @NotBlank(message = "密码必传")
    private String password;

    @NotBlank(message = "密码必传")
    private String confirmPassword;
}
