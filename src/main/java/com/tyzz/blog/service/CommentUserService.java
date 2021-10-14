package com.tyzz.blog.service;

import com.tyzz.blog.entity.dao.CommentUserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (CommentUser)表服务实现类
 *
 * @author makejava
 * @since 2021-10-14 20:48:50
 */
@Service("commentUserService")
public class CommentUserService {
    @Resource
    private CommentUserDao commentUserDao;
}