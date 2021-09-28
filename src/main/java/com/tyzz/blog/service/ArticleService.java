package com.tyzz.blog.service;

import com.tyzz.blog.dao.ArticleDao;
import com.tyzz.blog.entity.Article;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Article)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:46
 */
@Service("articleService")
public class ArticleService {
    @Resource
    private ArticleDao articleDao;

    public Article selectOneById(Long articleId) {
        return articleDao.selectById(articleId);
    }
}