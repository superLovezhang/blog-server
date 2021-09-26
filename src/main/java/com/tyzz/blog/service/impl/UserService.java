package com.tyzz.blog.service.impl;

import com.tyzz.blog.dao.UserDao;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.exception.BlogException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("userService")
public class UserService {
    @Resource
    private UserDao userDao;

    public void login(String email, String password) {
        User user = Optional.ofNullable(userDao.findOneByEmail(email))
                .orElseThrow(() -> new BlogException("该用户不存在"));

    }
}