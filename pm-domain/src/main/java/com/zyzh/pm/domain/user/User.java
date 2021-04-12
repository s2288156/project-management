package com.zyzh.pm.domain.user;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wcy
 **/
@Data
public class User {

    private String id;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    /**
     * 登录账户名
     **/
    private String username;

    /**
     * 密码
     **/
    private String password;

    /**
     * 头像
     **/
    private String icon;

    /**
     * 邮箱
     **/
    private String email;

    /**
     * 姓名
     **/
    private String name;

    private String inputPassword;
}
