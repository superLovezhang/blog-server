package com.tyzz.blog.config.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tyzz.blog.entity.User;
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
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 15:06
 */
public class BlogAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (StringUtils.isNotBlank(token)) {
            User user = JwtUtils.checkToken(token);
            List<SimpleGrantedAuthority> roleList = Collections.singletonList(new SimpleGrantedAuthority("USER"));
            BlogAuthenticationToken authenticationToken = new BlogAuthenticationToken(user.getUsername(), token, roleList);
            authenticationToken.setCurrentUser(user);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }
}
