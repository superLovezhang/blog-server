package com.tyzz.blog.config.security;

import com.tyzz.blog.util.ResponseUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description:
 * 权限访问失败处理器
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 10:39
 */
@Component
public class BlogAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        ResponseUtils.unauthenticatedResponse(httpServletResponse);
    }
}
