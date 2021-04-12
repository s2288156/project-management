package com.pm.config;

import com.alibaba.cola.dto.Response;
import com.pm.application.consts.ErrorCodeEnum;
import com.zyzh.exception.BizException;
import com.zyzh.exception.SysException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

/**
 * @author wcy
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    @ExceptionHandler(value = BizException.class)
    public Response bizException(BizException e) {
        log.error("BizException: {}", e.getMessage(), e);
        return Response.buildFailure(e.getErrCode(), e.getMessage());
    }

    @ExceptionHandler(value = SysException.class)
    public Response sysException(SysException e) {
        log.error("SysException: {}", e.getMessage(), e);
        return Response.buildFailure(e.getErrCode(), e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Response methodArgNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        log.error("argument not valid: {}", allErrors.toString());
        return Response.buildFailure(ErrorCodeEnum.ARGUMENT_NOT_VALID_ERROR.getErrorCode(), allErrors.get(0).getDefaultMessage());
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public Response paramsEx(MissingServletRequestParameterException e) {
        log.error("missing parameter: {}", e.getMessage());
        return Response.buildFailure(ErrorCodeEnum.ARGUMENT_NOT_VALID_ERROR.getErrorCode(), e.getMessage());
    }

}
