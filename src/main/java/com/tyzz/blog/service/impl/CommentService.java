package com.tyzz.blog.service.impl;

import com.tyzz.blog.dao.CommentDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Comment)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("commentService")
public class CommentService {
    @Resource
    private CommentDao commentDao;


}