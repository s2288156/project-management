package com.pm.application.consts;

import com.zyzh.exception.IStrErrorEnum;
import lombok.Getter;

/**
 * @author wcy
 */
public enum ErrorCodeEnum implements IStrErrorEnum {
    /**
     * 错误码
     */
    USERNAME_EXISTED("50000", "用户名重复"),
    PASSWORD_FAIL("50001", "密码错误"),
    USERNAME_NOT_FOUND("50002", "用户名不存在"),
    GROUP_NAME_EXISTED("50003", "组名称重复"),
    GROUP_NOT_FOUND("50004", "组不存在"),
    PROJECT_NAME_EXISTED("50005", "项目名重复"),
    MODULE_NAME_EXISTED("50006", "模块名重复"),
    MODULE_VERSION_EXISTED("50007", "模块版本重复"),
    MODULE_NOT_FOUND("50008", "模块不存在"),
    RE_DEPEND_NOT_ALLOW("50009", "不允许重复依赖"),
    PROJECT_NOT_FOUND("50010", "项目不存在"),
    ARGUMENT_NOT_VALID_ERROR("50011", "参数校验异常"),
    ;

    @Getter
    private String errorCode;

    @Getter
    private String errorMsg;

    ErrorCodeEnum(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    public String getCode() {
        return getErrorCode();
    }

    @Override
    public String getMsg() {
        return getErrorMsg();
    }

    @Override
    public IStrErrorEnum valueOfCode(String s) {
        return null;
    }
}
