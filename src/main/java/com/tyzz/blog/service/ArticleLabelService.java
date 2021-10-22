package com.tyzz.blog.service;

import com.tyzz.blog.dao.ArticleLabelDao;
import com.tyzz.blog.entity.Article;
import com.tyzz.blog.entity.ArticleLabel;
import com.tyzz.blog.entity.dto.ArticleDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (ArticleLabel)表服务实现类
 *
 * @author makejava
 * @since 2021-10-05 23:05:22
 */
@Service("articleLabelService")
public class ArticleLabelService {
    @Resource
    private ArticleLabelDao articleLabelDao;

    public void attach(Article article, ArticleDTO articleDTO) {
        articleDTO.getLabelIds().forEach(labelId -> {
            ArticleLabel articleLabel = new ArticleLabel();
            articleLabel.setArticleId(article.getArticleId());
            articleLabel.setLabelId(labelId);
            articleLabelDao.insert(articleLabel);
        });
    }
}