package com.tyzz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.UserAdminPageDTO;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
public interface UserDao extends BaseMapper<User> {
    User findOneByEmail(String email);

    BlogPage<User> listPage(UserAdminPageDTO dto, Page<User> page);
}