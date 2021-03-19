package com.pm.application.consts;

import lombok.Getter;

/**
 * @author wcy
 */
public enum ErrorCodeEnum {
    /**
     * 错误码
     */
    USERNAME_EXISTED("50000", "用户名重复"),
    PASSWORD_FAIL("50001", "密码错误"),
    USERNAME_NOT_FOUND("50002", "用户名不存在"),
    GROUP_NAME_EXISTED("50003", "组名称重复"),
    ;

    @Getter
    private String errorCode;

    @Getter
    private String errorMsg;

    ErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
}
