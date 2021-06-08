package com.zyzh.pm.domain.enums;

import lombok.Setter;

/**
 * @author wcy
 */
public enum RoleEnum {
    /**
     * 角色枚举
     */
    ADMIN("超级管理员"), GUEST("访客");

    @Setter
    private String desc;

    RoleEnum(String desc) {
        this.desc = desc;
    }
}
