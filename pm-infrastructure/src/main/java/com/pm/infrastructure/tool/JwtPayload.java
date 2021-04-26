package com.pm.infrastructure.tool;

import lombok.Data;

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

}
