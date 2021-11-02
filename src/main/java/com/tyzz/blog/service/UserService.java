package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.config.security.BlogAuthenticationToken;
import com.tyzz.blog.constant.BlogConstant;
import com.tyzz.blog.dao.UserDao;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.UserDTO;
import com.tyzz.blog.entity.vo.UserVO;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.util.JwtUtils;
import com.tyzz.blog.util.StringUtils;
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
    @Resource
    private RedisService redisService;
    @Resource
    private EmailService emailService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }

    public User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return null;
        }
        BlogAuthenticationToken blogAuthenticationToken = (BlogAuthenticationToken) authentication;
        User currentUser = blogAuthenticationToken.getCurrentUser();
        if (currentUser == null) {
            return null;
        }
        return userDao.selectById(currentUser.getUserId());
    }

    public User currentUserNotExistThrowException() {
        User user = currentUser();
        if (user == null) {
            throw new BlogException("请登录");
        }
        return user;
    }

    public Map<String, Object> login(String email, String password) {
        HashMap<String, Object> result = new HashMap<>();
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("email", email);
        User user = Optional.ofNullable(userDao.selectOne(wrapper))
                .orElseThrow(() -> new BlogException("用户不存在"));
        boolean matches = bCryptPasswordEncoder.matches(password, user.getPassword());
        if (!matches) {
            throw new BlogException("邮箱或密码错误");
        }
        result.put("token", JwtUtils.buildToken(user));
        result.put("user", userService.pojoToVO(user));
        return result;
    }

    public void register(UserDTO user) {
        validateRegisterInfo(user);
        buildUser(user);
    }

    private void buildUser(UserDTO user) {
        String encodePassword = bCryptPasswordEncoder.encode(user.getPassword());
        User build = User.builder()
                .password(encodePassword)
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
        userDao.insert(build);
    }

    private void validateRegisterInfo(UserDTO user) {
        String email = user.getEmail();
        QueryWrapper<User> wrapper = new QueryWrapper<User>().eq("email", email);
        Object verifyCode = redisService.get(BlogConstant.REGISTER_VERIFY_PREFIX + email);
        if (verifyCode == null || StringUtils.isEmpty(verifyCode.toString())) {
            throw new BlogException("请发送验证码");
        }
        if (!verifyCode.toString().equals(user.getVerifyCode())) {
            throw new BlogException("验证码不正确");
        }
        Integer count = userDao.selectCount(wrapper);
        if (count != 0) {
            throw new BlogException("当前邮箱已注册");
        }
        redisService.del(BlogConstant.REGISTER_VERIFY_PREFIX + email);
    }

    public User selectById(Long userId) {
        return userDao.selectById(userId);
    }

    public UserVO pojoToVO(User user) {
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    public void sendRegisterVerificationCode(String email) {
        String code = StringUtils.generateRandomCode(6);
        redisService.set(BlogConstant.REGISTER_VERIFY_PREFIX + email, code);
        emailService.sendPlainText(
                BlogConstant.EMAIL_VERIFICATION_SUBJECT,
                BlogConstant.EMAIL_VERIFICATION_TEXT + code,
                email
        );
    }

    public void saveByDTO(User user, UserDTO userDTO) {
        user.setAvatar(userDTO.getAvatar());
        user.setUsername(userDTO.getUsername());
        user.setBirthday(userDTO.getBirthday());
        user.setCity(userDTO.getCity());
        user.setDescription(userDTO.getDescription());
        user.setGender(userDTO.getGender());
        userDao.updateById(user);
    }
}