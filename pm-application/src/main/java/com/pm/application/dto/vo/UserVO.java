package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import com.nimbusds.jose.JWSObject;
import com.pm.infrastructure.tool.JsonUtils;
import com.pm.infrastructure.tool.Payload;
import com.zyzh.exception.SysException;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.ParseException;
import java.util.HashSet;
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

}
