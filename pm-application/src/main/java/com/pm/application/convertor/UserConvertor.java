package com.pm.application.convertor;

import com.pm.application.dto.cmd.UserLoginCmd;
import com.zyzh.pm.domain.user.User;
import org.springframework.beans.BeanUtils;

/**
 * @author wcy
 */
public class UserConvertor {
    public static User toEntity(UserLoginCmd cmd) {
        User user = new User();
        BeanUtils.copyProperties(cmd, user);
        return user;
    }
}
