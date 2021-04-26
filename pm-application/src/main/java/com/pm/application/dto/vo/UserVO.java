package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.nimbusds.jose.JWSObject;
import com.pm.infrastructure.dataobject.UserDO;
import com.pm.infrastructure.tool.JsonUtils;
import com.pm.infrastructure.tool.Payload;
import com.zyzh.exception.SysException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

/**
 * @author wcy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserVO extends DTO {
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

    private LocalDateTime createTime;

    public static UserVO convertForDo(UserDO userDO) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userDO, userVO);
        userVO.setAvatar(userDO.getIcon());
        return userVO;
    }
}
