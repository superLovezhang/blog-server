package com.tyzz.blog.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyzz.blog.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 10:52
 */
@Component
public class BlogAuthenticationHandler implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ResponseUtils.unauthenticatedResponse(httpServletResponse);
        return;
    }
}
