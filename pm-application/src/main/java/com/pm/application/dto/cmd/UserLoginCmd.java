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
public class UserLoginCmd extends Command {

    @NotBlank(message = "用户名必传")
    private String username;

    @NotBlank(message = "密码必传")
    private String password;
}
