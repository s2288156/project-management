package com.pm.infrastructure.security;

import lombok.Data;

import java.util.Set;

/**
 * @author wcy
 */
@Data
public class JwtPayload {

    private String uid;

    /**
     * 失效日期时间戳，单位秒
     */
    private Long exp;

    /**
     * 签发机构
     */
    private String iss;

    private Set<String> roles;
}
