package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.config.security.BlogAuthenticationToken;
import com.tyzz.blog.dao.AdministratorDao;
import com.tyzz.blog.entity.convert.AdministratorConverter;
import com.tyzz.blog.entity.pojo.Administrator;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.util.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * (Administrator)表服务实现类
 *
 * @author makejava
 * @since 2022-01-17 10:55:29
 */
@RequiredArgsConstructor
@Service("administratorService")
public class AdministratorService {
    private final AdministratorDao administratorDao;

    public Map<String, Object> login(String email, String password) {
        HashMap<String, Object> result = new HashMap<>();
        QueryWrapper<Administrator> wrapper = new QueryWrapper<Administrator>()
                .eq("email", email)
                .eq("password", password);
        Administrator admin = Optional.ofNullable(administratorDao.selectOne(wrapper))
                .orElseThrow(() -> new BlogException("账号或密码不正确"));
        result.put("token", JwtUtils.buildAdminToken(admin));
        result.put("admin", AdministratorConverter.INSTANCE.admin2AdminVO(admin));
        return result;
    }

    public Administrator currentAdmin() {
        BlogAuthenticationToken authentication = (BlogAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        return authentication.getAdmin();
    }
}