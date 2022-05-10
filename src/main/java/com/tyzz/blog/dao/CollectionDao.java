package com.tyzz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyzz.blog.entity.pojo.Collection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Collection)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
public interface CollectionDao extends BaseMapper<Collection> {
    List<Long> findAllUserIdsByArticle(@Param("articleId") Long articleId);
}