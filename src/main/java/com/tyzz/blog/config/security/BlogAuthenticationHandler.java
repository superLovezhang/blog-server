package com.tyzz.blog.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyzz.blog.common.Result;
import com.tyzz.blog.enums.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 * 认证失败处理器
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 10:52
 */
@Component
public class BlogAuthenticationHandler implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setStatus(200);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(Result.result(ResponseCode.LOGIN_FAIL)));
        return;
    }
}
