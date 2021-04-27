package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.security.SecurityUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LoginUserVO extends DTO {
    private String id;

    /**
     * 登录账户名
     **/
    private String username;

    /**
     * 头像
     **/
    private String avatar;

    /**
     * 邮箱
     **/
    private String email;

    /**
     * 姓名
     **/
    private String name;

    private String token;

    private Set<String> roles;

    public static LoginUserVO convertForDo(SecurityUser securityUser) {
        LoginUserVO userVO = new LoginUserVO();
        BeanUtils.copyProperties(securityUser, userVO);
        userVO.setRoles(securityUser.roles());
        return userVO;
    }
}
