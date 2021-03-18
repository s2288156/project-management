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
