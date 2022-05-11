package com.tyzz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.entity.pojo.Article;
import com.tyzz.blog.entity.dto.ArticlePageDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Article)表数据库访问层
 *
 * @author makejava
 * @since 2021-09-26 10:24:36
 */
public interface ArticleDao extends BaseMapper<Article> {
    BlogPage<Article> listPage(BlogPage<Article> page, ArticlePageDTO articlePageDTO);

    List<Article> hotList();

    Article selectOneListArticleById(@Param("id") Long id);

    List<Article> listRecordArticleInIds(@Param("ids") List<Long> ids);
}