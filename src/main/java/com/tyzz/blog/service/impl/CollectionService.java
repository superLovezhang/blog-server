package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.dao.CollectionDao;
import com.tyzz.blog.entity.dto.BasePageDTO;
import com.tyzz.blog.entity.pojo.Article;
import com.tyzz.blog.entity.pojo.Collection;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.entity.vo.CollectionVO;
import com.tyzz.blog.exception.BlogException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.tyzz.blog.constant.BlogConstant.ARTICLE_COLLECT;

/**
 * (Collection)表服务实现类
 *
 * @author makejava
 * @since 2021-09-26 10:24:49
 */
@Service("collectionService")
@RequiredArgsConstructor
public class CollectionService {
    private final CollectionDao collectionDao;
    @Autowired
    private ArticleService articleService;
    private final UserService userService;
    private final RedisService redisService;
    private final CommonService commonService;


    public BlogPage<CollectionVO> pageByUser(User user, BasePageDTO pageVO) {
        BlogPage<Collection> page = BlogPage.of(pageVO.getPage(), pageVO.getSize());
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", user.getUserId())
               .isNotNull("article_id");
        return collectionDao.selectPage(page, wrapper)
                .map(this::pojoToVO);
    }

    public void collect(User user, Long articleId) {
        commonService.commonSetOperation(articleId, user, ARTICLE_COLLECT, this::getUserIdsByArticleId);
    }

    public List<Long> getUserIdsByArticleId(Long id) {
        Article article = Optional.ofNullable(articleService.selectOneById(id))
                .orElseThrow(() -> new BlogException("该文章不存在"));
        return findAllUserIdsByArticle(article.getArticleId());
    }

    public List<Long> findAllUserIdsByArticle(Long articleId) {
        return collectionDao.findAllUserIdsByArticle(articleId);
    }

    public CollectionVO pojoToVO(Collection collection) {
        return CollectionVO.builder()
                .collectionId(collection.getCollectionId())
                .article(articleService.pojoToVO(articleService.selectOneById(collection.getArticleId())))
                .user(userService.pojoToVO(userService.selectById(collection.getUserId())))
                .build();
    }

    public long count(Long id, String collectKey) {
        commonService.initCountData(id, this::getUserIdsByArticleId, collectKey);
        return redisService.sGetSetSize(collectKey) - 1;
    }

    /**
     * 根据文章id和用户id删除
     * @param articleId 文章id
     * @param userId 用户id
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long articleId, Long userId) {
        QueryWrapper<Collection> wrapper = new QueryWrapper<>();
        wrapper.eq("article_id", articleId)
                .eq("user_id", userId);
        collectionDao.delete(wrapper);
    }

    /**
     * 根据文章id和用户id创建Collection
     * @param articleId 文章id {@link Article}
     * @param userId 用户id {@link User}
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public Collection create(Long articleId, Long userId) {
        return Collection.builder()
                .articleId(articleId)
                .userId(userId)
                .build();
    }

    /**
     * 保存collection对象
     * @param collection
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Collection collection) {
        collectionDao.insert(collection);
    }
}