package com.tyzz.blog.service;

import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.dao.ArticleDao;
import com.tyzz.blog.entity.Article;
import com.tyzz.blog.entity.Collection;
import com.tyzz.blog.entity.User;
import com.tyzz.blog.entity.dto.ArticleDTO;
import com.tyzz.blog.entity.dto.ArticlePageDTO;
import com.tyzz.blog.entity.vo.ArticleVO;
import com.tyzz.blog.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    @Resource
    private CommentService commentService;
    @Resource
    private CollectionService collectionService;
    @Resource
    private ArticleLabelService articleLabelService;

    public Article selectOneById(Long articleId) {
        return articleDao.selectById(articleId);
    }

    @Transactional
    public void save(User user, ArticleDTO articleDTO) {
        Article article = Article.builder()
                .linkAddress(articleDTO.getLinkAddress())
                .articleType(articleDTO.getArticleType())
                .articleName(articleDTO.getArticleName())
                .content(articleDTO.getContent())
                .htmlContent(articleDTO.getHtmlContent())
                .articleId(articleDTO.getArticleId())
                .categoryId(articleDTO.getCategoryId())
                .userId(user.getUserId())
                .cover(StringUtils.pickCoverFromHtml(articleDTO.getHtmlContent()))
                .build();
        articleDao.insert(article);
        articleLabelService.attach(article, articleDTO);
    }

    public BlogPage<Article> listPage(ArticlePageDTO articlePageDTO) {
        BlogPage<Article> page = BlogPage.of(articlePageDTO.getPage(), articlePageDTO.getSize());
        return articleDao.listPage(page, articlePageDTO);
    }

    public List<Article> hotList() {
        return articleDao.hotList();
    }

    public ArticleVO pojoToVO(Article article) {
        if (article == null) {
            return null;
        }
        Long userId = article.getUserId();
        User currentUser = userService.currentUser();
        Collection collection = collectionService.findOneByUserAndArticle(currentUser, article);
        User user = userService.selectById(userId);
        return ArticleVO.builder()
                    .articleId(article.getArticleId())
                    .articleName(article.getArticleName())
                    .content(article.getContent())
                    .htmlContent(article.getHtmlContent())
                    .cover(article.getCover())
                    .createTime(article.getCreateTime())
                    .updateTime(article.getUpdateTime())
                    .likes(collectionService.countByArticle(article))
                    .collected(collection != null)
                    .user(userService.pojoToVO(user))
                    .previewContent(article.getPreviewContent())
                    .viewCount(article.getViewCount())
                    .commentCount(commentService.countByArticleId(article.getArticleId()))
                    .build();
    }

    public Article viewArticleDetail(Long articleId) {
        Article article = selectOneById(articleId);
        article.setViewCount(article.getViewCount() + 1);
        articleDao.updateById(article);
        return article;
    }
}