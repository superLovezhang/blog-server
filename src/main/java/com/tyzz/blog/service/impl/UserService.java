package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.dao.UserDao;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.vo.UserVO;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.exception.BlogLoginInvalidException;
import com.tyzz.blog.util.JwtUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
public class UserService implements UserDetailsService {
    @Resource
    private UserDao userDao;
    @Resource
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public String login(String email, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("email", email);
        User user = Optional.ofNullable(userDao.selectOne(wrapper))
                .orElseThrow(() -> new UsernameNotFoundException("用户不存在"));
        boolean matches = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (!matches) {
            throw new BlogLoginInvalidException("密码错误");
        }
        return JwtUtils.buildToken(user);
    }

    public void register(UserVO user) {
        String email = user.getEmail();
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("email", email);
        Integer count = userDao.selectCount(wrapper);
        if (count != 0) {
            throw new BlogException("当前邮箱已注册");
        }
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        User build = User.builder()
                .password(encodePassword)
                .email(email)
                .username(user.getUsername())
                .build();
        userDao.insert(build);
    }
}