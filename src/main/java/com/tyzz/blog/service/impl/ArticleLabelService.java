package com.tyzz.blog.service.impl;

import com.tyzz.blog.dao.ArticleLabelDao;
import com.tyzz.blog.entity.pojo.Article;
import com.tyzz.blog.entity.pojo.ArticleLabel;
import com.tyzz.blog.entity.pojo.Label;
import com.tyzz.blog.entity.dto.ArticleDTO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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

    public List<Label> labelsByArticle(Article article) {
        return articleLabelDao.labelsByArticleId(article.getArticleId());
    }
}