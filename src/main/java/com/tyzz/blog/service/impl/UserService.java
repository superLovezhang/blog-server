package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.config.security.BlogAuthenticationToken;
import com.tyzz.blog.constant.BlogConstant;
import com.tyzz.blog.dao.UserDao;
import com.tyzz.blog.entity.dto.UserAdminPageDTO;
import com.tyzz.blog.entity.dto.UserDTO;
import com.tyzz.blog.entity.dto.UserPasswordDTO;
import com.tyzz.blog.entity.pojo.LoginRecord;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.entity.vo.UserVO;
import com.tyzz.blog.enums.UserStatus;
import com.tyzz.blog.enums.VerifyCodeType;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.exception.BlogLoginInvalidException;
import com.tyzz.blog.util.HttpClientUtils;
import com.tyzz.blog.util.JwtUtils;
import com.tyzz.blog.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static com.tyzz.blog.constant.BlogConstant.PWD_VERIFY_PREFIX;
import static com.tyzz.blog.constant.BlogConstant.REGISTER_VERIFY_PREFIX;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("userService")
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserDao userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RedisService redisService;
    private final EmailService emailService;
    private final HttpServletRequest request;
    private final ObjectMapper om;
    private final LoginRecordService loginRecordService;

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
        result.put("user", pojoToVO(user));
        checkAndStorageRecord(user.getUserId(), request.getRemoteAddr(), loginRecordService::save);
        return result;
    }

    /**
     * todo 后面改成MQ
     * 检查登陆记录并保存本次记录
     * @param userId 用户id
     * @param ip 登录ip
     * @param save 保存方法
     */
    @Async
    @SuppressWarnings("unchecked")
    public void checkAndStorageRecord(Long userId, String ip, Function<LoginRecord, Object> save) {
        String res = HttpClientUtils.get(BlogConstant.OPEN_URL_ADDRESS + ip);
        try {
            Map<String, String> response = om.readValue(res, Map.class);
            if (response.containsKey("data") && StringUtils.isNotEmpty(response.get("data"))) {
                String address = response.get("data");
                String[] addressArr = address.split(" ");
                if (addressArr.length >= 1) {
                    checkRecord(userId, addressArr[0]);
                    saveRecord(userId, save, addressArr[0]);
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查登录地址
     * @param userId 用户id
     * @param address 本次登陆地址
     */
    private void checkRecord(Long userId, String address) {
        LoginRecord record = loginRecordService.lastRecord(userId);
        if (record != null && !address.equals(record.getAddress())) {
            User user = selectById(userId);
            // 发邮件
            String content = String.format(BlogConstant.OFFSITE_LOGIN_NOTIFICATION, address);
            emailService.sendPlainText(BlogConstant.OFFSITE_LOGIN_SUBJECT, content, user.getEmail());
        }
    }

    /**
     * 保存本次登录记录
     * @param userId 用户id
     * @param save 保存方法
     * @param address 登录地址
     */
    private void saveRecord(Long userId, Function<LoginRecord, Object> save, String address) {
        LoginRecord record = LoginRecord.builder()
                .userId(userId)
                .address(address)
                .build();
        save.apply(record);
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