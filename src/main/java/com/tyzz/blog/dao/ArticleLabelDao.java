package com.tyzz.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tyzz.blog.entity.ArticleLabel;
import com.tyzz.blog.entity.Label;

import java.util.List;

/**
 * (ArticleLabel)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-05 23:05:22
 */
public interface ArticleLabelDao extends BaseMapper<ArticleLabel> {
    List<Long> listHotIds();

    List<Label> labelsByArticleId(Long articleId);
}