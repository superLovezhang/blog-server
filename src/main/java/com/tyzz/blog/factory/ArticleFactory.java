package com.tyzz.blog.factory;

import com.tyzz.blog.entity.dto.ArticleDTO;
import com.tyzz.blog.entity.pojo.Article;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.enums.ArticleStatus;
import com.tyzz.blog.util.StringUtils;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-03-06 21:37
 */
public class ArticleFactory {
    public static Article articleDTO2PO(ArticleDTO articleDTO, User user) {
        return Article.builder()
                .linkAddress(articleDTO.getLinkAddress())
                .articleType(articleDTO.getArticleType())
                .articleName(articleDTO.getArticleName())
                .content(articleDTO.getContent())
                .htmlContent(articleDTO.getHtmlContent())
                .articleId(articleDTO.getArticleId())
                .categoryId(articleDTO.getCategoryId())
                .userId(user.getUserId())
                .cover(StringUtils.pickCoverFromHtml(articleDTO.getHtmlContent()))
                .articleId(articleDTO.getArticleId())
                .status(ArticleStatus.REVIEW)
                .build();
    }
}
