package com.pm.infrastructure.consts;

import com.zyzh.exception.IStrErrorEnum;
import lombok.Getter;

/**
 * @author wcy
 */
public enum ErrorCodeEnum implements IStrErrorEnum {
    /**
     * 错误码
     */
    UNAUTHORIZED("40001", "没有权限"),
    JWT_VERIFIER_ERROR("40002", "token验证异常"),
    JWT_PARSE_ERROR("40003", "token解析异常"),
    USERNAME_EXISTED("50000", "用户名重复"),
    PASSWORD_FAIL("50001", "密码错误"),
    USERNAME_NOT_FOUND("50002", "用户不存在"),
    GROUP_NAME_EXISTED("50003", "组名称重复"),
    GROUP_NOT_FOUND("50004", "组不存在"),
    PROJECT_NAME_EXISTED("50005", "项目名重复"),
    MODULE_NAME_EXISTED("50006", "模块名重复"),
    MODULE_VERSION_EXISTED("50007", "模块版本重复"),
    MODULE_NOT_FOUND("50008", "模块不存在"),
    RE_DEPEND_NOT_ALLOW("50009", "不允许重复依赖"),
    PROJECT_NOT_FOUND("50010", "项目不存在"),
    ARGUMENT_NOT_VALID_ERROR("50011", "参数校验异常"),
    LATEST_MODULE_VERSION_NOT_ALLOW_DELETE("50012", "最新版本不允许删除"),
    MODULE_DEPEND_NOT_ALLOW_DEL("50013", "此模块版本被引用，不允许删除"),
    TWO_PASSWORD_ENTERED_NOT_SAME("50014", "两次输入的密码不相同"),
    MODULE_DEPENDENCE_ERROR("50015", "模块正在被其它项目引用"),
    PROJECT_MODULE_DEPENDENCE_ERROR("50016", "项目模块被正在被引用"),
    HAVE_DEPEND_GROUP_NOT_ALLOW_DELETE("50017", "group存在被引用Module，不允许被删除"),
    ROLE_NOT_ALLOW_DELETE("50018", "Role不允许删除"),
    ROLE_EXISTED("50018", "Role已经存在"),
    ;

    @Getter
    private String code;

    @Getter
    private String msg;

    ErrorCodeEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public IStrErrorEnum valueOfCode(String s) {
        return null;
    }
}
