package com.tyzz.blog.config.security;

import com.tyzz.blog.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-06 13:11
 */
public class BlogAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private User currentUser;

    public BlogAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public BlogAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}