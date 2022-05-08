package com.tyzz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyzz.blog.entity.pojo.UserLike;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CommentUser)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-14 20:49:22
 */
public interface UserLikeDao extends BaseMapper<UserLike> {
    List<Long> findAllUserIdsByArticle(@Param("articleId") Long articleId);

    List<Long> findAllUserIdsByComment(@Param("commentId") Long commentId);
}