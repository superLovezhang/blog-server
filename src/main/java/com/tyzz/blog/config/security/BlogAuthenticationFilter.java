package com.tyzz.blog.config.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.util.JwtUtils;
import com.tyzz.blog.util.ResponseUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 15:06
 */
public class BlogAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            ResponseUtils.unauthenticatedResponse(response);
            return;
        }
        User user = JwtUtils.checkToken(token);
        //todo 拿到用户信息做一些事
        List<SimpleGrantedAuthority> roleList = Arrays.asList(new SimpleGrantedAuthority("USER"));
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(null, user, roleList);
        authenticationToken.setAuthenticated(true);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
