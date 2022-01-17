package com.tyzz.blog.service;

import com.tyzz.blog.dao.AdministratorDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}