package com.tyzz.blog.dao;

import java.util.List;

/**
 * (ArticleLabel)表数据库访问层
 *
 * @author makejava
 * @since 2021-10-05 23:05:22
 */
public interface ArticleLabelDao {
    List<Long> listHotIds();
}