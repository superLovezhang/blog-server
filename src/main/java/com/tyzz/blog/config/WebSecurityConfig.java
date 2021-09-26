package com.tyzz.blog.config;

import com.tyzz.blog.entity.constant.BlogConstant;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(BlogConstant.AUTHORIZED_URL_PATTERN)
                .hasRole("ADMIN")
                .antMatchers(BlogConstant.UNAUTHORIZED_URL_PATTERN)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .and()
                .csrf()
                .disable()
        ;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
