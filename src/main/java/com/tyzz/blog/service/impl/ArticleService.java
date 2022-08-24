package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.dao.ArticleDao;
import com.tyzz.blog.entity.convert.ArticleConverter;
import com.tyzz.blog.entity.dto.ArticleAdminPageDTO;
import com.tyzz.blog.entity.dto.ArticleDTO;
import com.tyzz.blog.entity.dto.ArticlePageDTO;
import com.tyzz.blog.entity.pojo.*;
import com.tyzz.blog.entity.vo.ArticleListVO;
import com.tyzz.blog.entity.vo.ArticleRecordVO;
import com.tyzz.blog.entity.vo.ArticleVO;
import com.tyzz.blog.enums.ArticleStatus;
import com.tyzz.blog.enums.LikeType;
import com.tyzz.blog.enums.NotificationType;
import com.tyzz.blog.enums.NotifyBehavior;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.factory.ArticleFactory;
import com.tyzz.blog.service.ILike;
import com.tyzz.blog.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tyzz.blog.constant.BlogConstant.ARTICLE_COLLECT;
import static com.tyzz.blog.constant.BlogConstant.ARTICLE_LIKE;
import static com.tyzz.blog.constant.MqConstant.ARTICLE_AUDIT_KEY;
import static com.tyzz.blog.constant.MqConstant.ARTICLE_NOTIFICATION_EXCHANGE;


/**
 * (Article)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:46
 */
@RequiredArgsConstructor
@Service("articleService")
public class ArticleService implements ILike {
    private final ArticleDao articleDao;
    private final UserService userService;
    private final LabelService labelService;
    private final CategoryService categoryService;
    private final ArticleLabelService articleLabelService;
    private final NotificationService notificationService;
    private final UserLikeService userLikeService;
    private final CommonService commonService;
    @Autowired
    private CollectionService collectionService;
    private final RedisService redisService;
    private final MessageService messageService;
//    private final RabbitTemplate rabbitTemplate;

    public Article selectOneById(Long articleId) {
        return articleDao.selectById(articleId);
    }

    @Transactional
    public void save(User user, ArticleDTO articleDTO) {
        Article article = ArticleFactory.articleDTO2PO(articleDTO, user);
        saveArticle(article);
        articleLabelService.attach(article, articleDTO);
    }

    private void saveArticle(Article article) {
        if (null != article.getArticleId()) {
            articleDao.updateById(article);
        } else {
            articleDao.insert(article);
        }
    }

    public BlogPage<Article> listPage(ArticlePageDTO articlePageDTO) {
        BlogPage<Article> page = BlogPage.of(articlePageDTO.getPage(), articlePageDTO.getSize());
        return articleDao.listPage(page, articlePageDTO);
    }

    public List<Article> hotList() {
        return articleDao.hotList();
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

    /**
     * 审核文章
     * @param articleId 文章id
     * @param status 状态
     * @param refuseReason 拒绝原因
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void audit(Long articleId, ArticleStatus status, String refuseReason) {
        Article article = Optional.ofNullable(articleDao.selectById(articleId))
                .orElseThrow(() -> new BlogException("该文章不存在"));
        article.setStatus(status);
        sendNotificationMessage(refuseReason, article);
        article.setRefuseReason(refuseReason);
        articleDao.updateById(article);
    }

    /**
     * 发送文章审核消息
     * @param refuseReason 拒绝原因
     * @param article 文章
     */
    @Transactional(propagation = Propagation.REQUIRED)
    void sendNotificationMessage(String refuseReason, Article article) {
        // 1.先创建需要发送的内容类
        Notification notification = createNotification(refuseReason, article);
        // 2.将消息存储到数据库中
        Message message = messageService.saveMessage(
                messageService.create(notification,
                        ARTICLE_NOTIFICATION_EXCHANGE, ARTICLE_AUDIT_KEY));
        // 3.同时获取数据库id 并发送消息到mq
//        rabbitTemplate.convertAndSend(ARTICLE_NOTIFICATION_EXCHANGE,
//                ARTICLE_AUDIT_KEY, notification,
//                new CorrelationData(message.getMessageId().toString()));
    }

    /**
     * 创建一个通知实体类
     * @param refuseReason 拒绝原因
     * @param article 文章
     * @return Notification
     */
    private Notification createNotification(String refuseReason, Article article) {
        Notification notification;
        // 发送通知
        if (StringUtils.isNotEmpty(refuseReason)) {
            notification = notificationService.createDeny(
                    NotificationType.ARTICLE,
                    refuseReason,
                    NotifyBehavior.REJECT,
                    userService.selectById(article.getUserId())
            );
        } else {
            notification = notificationService.createSuccess(
                    NotificationType.ARTICLE,
                    userService.selectById(article.getUserId()));
        }
        return notification;
    }

    public Article selectOneByIdAndUser(Long id, User user) {
        QueryWrapper<Article> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", id);
        wrapper.eq("user_id", user.getUserId());
        return Optional.ofNullable(articleDao.selectOne(wrapper))
                .orElseThrow(() -> new BlogException("当前文章不存在"));
    }

    public Article selectOneListArticleById(Long id) {
        return articleDao.selectOneListArticleById(id);
    }

    public void remove(long articleId, User user) {
        QueryWrapper<Article> wrapper = new QueryWrapper<Article>()
                .eq("article_id", articleId)
                .eq("user_id", user.getUserId());
        articleDao.delete(wrapper);
    }

    @Override
    public void like(Long id, User user) {
        commonService.commonSetOperation(id, user, LikeType.ARTICLE.getType(), this::getUserIdsByArticleId);
    }

    public List<Long> getUserIdsByArticleId(Long id) {
        Article article = Optional.ofNullable(selectOneById(id))
                .orElseThrow(() -> new BlogException("文章不存在"));
        return userLikeService.findAllUserIdsByArticle(article.getArticleId());
    }


    public ArticleVO pojoToVO(Article article) {
        if (article == null) {
            return null;
        }
        List<Label> labels = articleLabelService.labelsByArticle(article);
        Long userId = article.getUserId();
        User currentUser = userService.currentUser();
        User user = userService.selectById(userId);
        Long articleId = article.getArticleId();
        String likeKey = StringUtils.generateRedisKey(ARTICLE_LIKE, articleId);
        String collectKey = StringUtils.generateRedisKey(ARTICLE_COLLECT, articleId);

        ArticleVO articleVO = ArticleConverter.INSTANCE.article2VO(article);
        articleVO.setLikes(userLikeService.count(articleId, likeKey, LikeType.ARTICLE));
        articleVO.setCollects(collectionService.count(articleId, collectKey));
        if (currentUser != null) {
            articleVO.setCollected(redisService.sHasKey(collectKey, currentUser.getUserId()));
            articleVO.setLiked(redisService.sHasKey(likeKey, currentUser.getUserId()));
        }
        articleVO.setUser(userService.pojoToVO(user));
        articleVO.setCommentCount(article.getCommentCount());
        articleVO.setCategory(categoryService.pojoToVO(categoryService.selectOneById(article.getCategoryId())));
        articleVO.setLabels(labels.stream().map(labelService::pojoToVO).collect(Collectors.toList()));
        return articleVO;
    }

    public ArticleListVO pojo2ListVO(Article article) {
        if (article == null) {
            return null;
        }
        String likeKey = StringUtils.generateRedisKey(ARTICLE_LIKE, article.getArticleId());
        String collectKey = StringUtils.generateRedisKey(ARTICLE_COLLECT, article.getArticleId());

        return ArticleListVO.builder()
                    .cover(article.getCover())
                    .articleId(article.getArticleId())
                    .commentCount(article.getCommentCount())
                    .previewContent(article.getPreviewContent())
                    .articleName(article.getArticleName())
                    .viewCount(article.getViewCount())
                    .likes(userLikeService.count(article.getArticleId(), likeKey, LikeType.ARTICLE))
                    .collects(collectionService.count(article.getArticleId(), collectKey))
                    .createTime(article.getCreateTime())
                    .updateTime(article.getUpdateTime())
                    .build();
    }

    public ArticleRecordVO pojo2RecordVO(Article article) {
        if (article == null) {
            return null;
        }

        return ArticleRecordVO.builder()
                .articleId(article.getArticleId())
                .articleName(article.getArticleName())
                .build();
    }

    /**
     * 自增文章评论数
     * @param articleId 文章id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void incrCommentCount(Long articleId) {
        updateCommentCount(articleId, 1);
    }

    /**
     * 自减文章评论数
     * @param articleId 文章id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void decreCommentCount(Long articleId) {
        updateCommentCount(articleId, -1);
    }

    /**
     * 更新文章评论数
     * @param articleId 文章id
     * @param operateNum 操作数
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateCommentCount(Long articleId, int operateNum) {
        Article article = Optional.ofNullable(articleDao.selectById(articleId))
                .orElseThrow(() -> new BlogException("当前文章不存在"));
        article.setCommentCount(article.getCommentCount() + operateNum);
        articleDao.updateById(article);
    }

    /**
     * 通过文章ids获取文章列表
     * @param ids 文章id列表
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Article> listRecordArticleInIds(List<Long> ids) {
        return articleDao.listRecordArticleInIds(ids);
    }
}