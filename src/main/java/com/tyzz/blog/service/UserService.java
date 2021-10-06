package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.config.security.BlogAuthenticationToken;
import com.tyzz.blog.dao.UserDao;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.UserDTO;
import com.tyzz.blog.entity.vo.UserVO;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.util.JwtUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
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
    @Resource
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            throw new BlogException("请登录");
        }
        BlogAuthenticationToken blogAuthenticationToken = (BlogAuthenticationToken) authentication;
        User currentUser = Optional.ofNullable(blogAuthenticationToken.getCurrentUser())
                .orElseThrow(() -> new BlogException("请登录"));
        return userDao.selectById(currentUser.getUserId());
    }

    public Map<String, Object> login(String email, String password) {
        HashMap<String, Object> result = new HashMap<>();
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("email", email);
        User user = Optional.ofNullable(userDao.selectOne(wrapper))
                .orElseThrow(() -> new BlogException("用户不存在"));
        boolean matches = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (!matches) {
            throw new BlogException("密码错误");
        }
        result.put("token", JwtUtils.buildToken(user));
        result.put("user", userService.pojoToDTO(user));
        return result;
    }

    public void register(UserDTO user) {
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

    public User selectById(Long userId) {
        return userDao.selectById(userId);
    }

    public UserVO pojoToDTO(User user) {
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
}