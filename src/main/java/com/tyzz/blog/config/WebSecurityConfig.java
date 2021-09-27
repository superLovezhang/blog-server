package com.tyzz.blog.config;

import com.tyzz.blog.config.security.AuthenticateFilter;
import com.tyzz.blog.config.security.BlogAccessDeniedHandler;
import com.tyzz.blog.config.security.BlogAuthenticationHandler;
import com.tyzz.blog.constant.BlogConstant;
import com.tyzz.blog.service.impl.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-26 10:30
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserService userService;
    private final BlogAccessDeniedHandler blogAccessDeniedHandler;
    private final BlogAuthenticationHandler blogAuthenticationHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(BlogConstant.AUTHORIZED_URL_PATTERN)
                .hasRole("ADMIN")
                .antMatchers(BlogConstant.UNAUTHORIZED_URL_PATTERN)
                .permitAll()
                .anyRequest()
                .authenticated()
        ;
        http.exceptionHandling().accessDeniedHandler(blogAccessDeniedHandler).authenticationEntryPoint(blogAuthenticationHandler);
        http.addFilterBefore(new AuthenticateFilter(), UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
