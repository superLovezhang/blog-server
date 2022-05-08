package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.config.security.BlogAuthenticationToken;
import com.tyzz.blog.dao.UserDao;
import com.tyzz.blog.entity.dto.UserAdminPageDTO;
import com.tyzz.blog.entity.dto.UserDTO;
import com.tyzz.blog.entity.dto.UserPasswordDTO;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.entity.vo.UserVO;
import com.tyzz.blog.enums.UserStatus;
import com.tyzz.blog.enums.VerifyCodeType;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.exception.BlogLoginInvalidException;
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

import static com.tyzz.blog.constant.BlogConstant.*;

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
        User user = userDao.selectById(currentUser.getUserId());
        if (user != null && user.getStatus().equals(UserStatus.FROZEN)) {
            throw new BlogLoginInvalidException("账户已被冻结，请联系管理员");
        }
        return user;
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
        if (user.getStatus().equals(UserStatus.FROZEN)) {
            throw new BlogException("账户已被冻结，请联系管理员");
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
        Object verifyCode = redisService.get(REGISTER_VERIFY_PREFIX + email);
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
        redisService.del(REGISTER_VERIFY_PREFIX + email);
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

    public void sendRegisterVerificationCode(String email, Integer codeNumber) {
        String code = StringUtils.generateRandomCode(6);
        VerifyCodeType verifyCodeType = VerifyCodeType.getInstanceByNumber(codeNumber);
        redisService.set(verifyCodeType.getCode() + email, code);
        emailService.sendPlainText(
                verifyCodeType.getSubject(),
                verifyCodeType.getText() + code,
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

    public void updatePassword(UserPasswordDTO userDTO, User user) {
        Object sourceVerifyCode = redisService.get(PWD_VERIFY_PREFIX + user.getEmail());
        if (sourceVerifyCode == null || StringUtils.isEmpty(sourceVerifyCode.toString())) {
            throw new BlogException("请发送验证码");
        }
        user.setPassword(bCryptPasswordEncoder.encode(userDTO.getPassword()));
        userDao.updateById(user);
    }

    public BlogPage<User> listPage(UserAdminPageDTO dto) {
        BlogPage<User> page = BlogPage.of(dto.getPage(), dto.getSize());
        return userDao.listPage(dto, page);
    }

    public void ban(long userId, UserStatus status, String frozenReason) {
        User user = Optional.ofNullable(userDao.selectById(userId))
                .orElseThrow(() -> new BlogException("该用户不存在"));
        user.setStatus(status);
        user.setFrozenReason(frozenReason);
        userDao.updateById(user);
    }
}