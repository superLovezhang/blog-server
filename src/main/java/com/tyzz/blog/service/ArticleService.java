package com.tyzz.blog.service;

import com.tyzz.blog.dao.ArticleDao;
import com.tyzz.blog.entity.Article;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.ArticleDTO;
import com.tyzz.blog.entity.vo.ArticleVO;
import org.springframework.beans.BeanUtils;
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
    @Resource
    private UserService userService;

    public Article selectOneById(Long articleId) {
        return articleDao.selectById(articleId);
    }

    public void save(User user, ArticleDTO articleDTO) {
        Article article = Article.builder()
                .articleName(articleDTO.getArticleName())
                .content(articleDTO.getContent())
                .articleId(articleDTO.getArticleId())
                .build();
        articleDao.insert(article);
    }

    public ArticleVO pojoToDTO(Article article) {
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(article, articleVO);
        User user = userService.selectById(article.getUserId());
        articleVO.setUser(userService.pojoToDTO(user));
        return articleVO;
    }
}