package com.tyzz.blog.config.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tyzz.blog.entity.pojo.Administrator;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.util.JwtUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Description:
 * 获取用户登录信息拦截器
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 15:06
 */
public class BlogAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        String platform = request.getHeader("platform");
        if (StringUtils.isNotBlank(token)) {
            BlogAuthenticationToken authenticationToken;
            // determine current request whether from admin platform
            // then apply different ways to parse token.
            if (isBackEnd(platform)) {
                authenticationToken = buildAdminAuthenticationToken(token);
            } else {
                authenticationToken = buildUserAuthenticationToken(token);
            }
            // set parsed token in to security context in the last.
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        // hand over to next filter to handle the request.
        filterChain.doFilter(request, response);
    }

    private BlogAuthenticationToken buildAdminAuthenticationToken(String token) {
        Administrator admin = JwtUtils.checkAdminToken(token);
        List<SimpleGrantedAuthority> roleList = Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
        BlogAuthenticationToken authenticationToken = new BlogAuthenticationToken(admin.getAdminName(), token, roleList);
        authenticationToken.setAdmin(admin);
        return authenticationToken;
    }

    private BlogAuthenticationToken buildUserAuthenticationToken(String token) {
        User user = JwtUtils.checkToken(token);
        List<SimpleGrantedAuthority> roleList = Collections.singletonList(new SimpleGrantedAuthority("USER"));
        BlogAuthenticationToken authenticationToken = new BlogAuthenticationToken(user.getUsername(), token, roleList);
        authenticationToken.setCurrentUser(user);
        return authenticationToken;
    }

    public boolean isBackEnd(String platform) {
        return "ADMIN".equals(platform);
    }
}
