package com.tyzz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.entity.dto.CommentAdminPageDTO;
import com.tyzz.blog.entity.pojo.Comment;

/**
 * (Comment)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
public interface CommentDao extends BaseMapper<Comment> {
    BlogPage<Comment> adminList(CommentAdminPageDTO dto, BlogPage<Comment> page);
}