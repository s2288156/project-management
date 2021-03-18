package com.pm.application.dto;

import com.alibaba.cola.dto.Command;
import com.pm.infrastructure.dataobject.UserDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import javax.validation.constraints.NotBlank;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserRegisterCmd extends Command {

    @NotBlank(message = "用户名必传")
    private String username;

    @NotBlank(message = "密码必传")
    private String password;

    public UserDO convert2Do() {
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(this, userDO);
        return userDO;
    }
}
