package com.tyzz.blog.config.security;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 10:08
 */
public class AuthenticateFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("运行了我自己的filter======================================");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
