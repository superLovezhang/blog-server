package com.tyzz.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.dao.ArticleDao;
import com.tyzz.blog.entity.convert.ArticleConverter;
import com.tyzz.blog.entity.dto.ArticleAdminPageDTO;
import com.tyzz.blog.entity.dto.ArticleDTO;
import com.tyzz.blog.entity.dto.ArticlePageDTO;
import com.tyzz.blog.entity.pojo.Article;
import com.tyzz.blog.entity.pojo.Collection;
import com.tyzz.blog.entity.pojo.Label;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.entity.vo.ArticleVO;
import com.tyzz.blog.enums.ArticleStatus;
import com.tyzz.blog.enums.NotificationType;
import com.tyzz.blog.enums.NotifyBehavior;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * (Article)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:46
 */
@RequiredArgsConstructor
@Service("articleService")
public class ArticleService {
    private final ArticleDao articleDao;
    private final UserService userService;
    private final CommentService commentService;
    private final LabelService labelService;
    private final CategoryService categoryService;
    private final CollectionService collectionService;
    private final ArticleLabelService articleLabelService;
    private final NotificationService notificationService;

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
        List<Label> labels = articleLabelService.labelsByArticle(article);
        Long userId = article.getUserId();
        User currentUser = userService.currentUser();
        Collection collection = collectionService.findOneByUserAndArticle(currentUser, article);
        User user = userService.selectById(userId);
        ArticleVO articleVO = ArticleConverter.INSTANCE.article2VO(article);
        articleVO.setLikes(collectionService.countByArticle(article));
        articleVO.setUser(userService.pojoToVO(user));
        articleVO.setCommentCount(commentService.countByArticleId(article.getArticleId()));
        articleVO.setCategory(categoryService.pojoToVO(categoryService.selectOneById(article.getCategoryId())));
        articleVO.setLabels(labels.stream().map(labelService::pojoToVO).collect(Collectors.toList()));
        articleVO.setCollected(collection != null);
        return articleVO;
    }

    public Article viewArticleDetail(Long articleId) {
        Article article = selectOneById(articleId);
        article.setViewCount(article.getViewCount() + 1);
        articleDao.updateById(article);
        return article;
    }

    public BlogPage<Article> adminList(ArticleAdminPageDTO dto) {
        BlogPage<Article> page = BlogPage.of(dto.getPage(), dto.getSize());
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        if (dto.getArticleType() != null) {
            wrapper.eq("article_type", dto.getArticleType());
        }
        if (dto.getSearchValue() != null) {
            wrapper.like("article_name", dto.getSearchValue());
        }
        if (dto.getStatus() != null) {
            wrapper.eq("status", dto.getStatus());
        }
        return articleDao.selectPage(page, wrapper);
    }

    @Transactional
    public void audit(Long articleId, ArticleStatus status, String refuseReason) {
        Article article = Optional.ofNullable(articleDao.selectById(articleId))
                .orElseThrow(() -> new BlogException("该文章不存在"));
        article.setStatus(status);
        // 发送通知
        if (StringUtils.isNotEmpty(refuseReason)) {
            notificationService.createDeny(
                    NotificationType.ARTICLE,
                    refuseReason,
                    NotifyBehavior.REJECT,
                    userService.selectById(article.getUserId())
            );
        } else {
            refuseReason = "";
            notificationService.createSuccess(
                    NotificationType.ARTICLE,
                    userService.selectById(article.getUserId()));
        }
        article.setRefuseReason(refuseReason);
        articleDao.updateById(article);
    }
}