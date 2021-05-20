package com.pm.application.dto.vo;

import com.alibaba.cola.dto.DTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

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

}
