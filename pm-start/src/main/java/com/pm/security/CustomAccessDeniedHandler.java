package com.pm.security;

import com.alibaba.cola.dto.Response;
import com.pm.infrastructure.consts.ErrorCodeEnum;
import com.pm.infrastructure.tool.JsonUtils;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author wcy
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(JsonUtils.toJson(Response.buildFailure(ErrorCodeEnum.UNAUTHORIZED.getCode(), ErrorCodeEnum.UNAUTHORIZED.getMsg())));
        writer.flush();
    }
}
