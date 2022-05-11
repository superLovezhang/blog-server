package com.tyzz.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tyzz.blog.common.BlogPage;
import com.tyzz.blog.dao.CollectionDao;
import com.tyzz.blog.entity.dto.BasePageDTO;
import com.tyzz.blog.entity.pojo.Article;
import com.tyzz.blog.entity.pojo.Collection;
import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.entity.vo.ArticleRecordVO;
import com.tyzz.blog.entity.vo.CollectionVO;
import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

import static com.tyzz.blog.constant.BlogConstant.*;

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

    /**
     * 收藏取消收藏文章
     * @param user 操作用户
     * @param articleId 文章id
     */
    public void collect(User user, Long articleId) {
        boolean operation = commonService.commonSetOperation(articleId, user, ARTICLE_COLLECT, this::getUserIdsByArticleId);
        afterCollectOperation(operation, user, articleId);
    }

    /**
     * 收藏操作之后的业务逻辑
     * @param operation 操作结果
     * @param user 操作用户
     * @param articleId 文章id
     */
    private void afterCollectOperation(boolean operation, User user, Long articleId) {
        //收藏成功
        String key = StringUtils.generateRedisKey(USER_COLLECT_ARTICLE, user.getUserId());
        initCollectData(key, user);
        if (operation) {
            //向zset类型结构内添加文章id
            addCollectionRecord(key, articleId);
        } else {
            //取消收藏
            //删除文章id
            delCollectionRecord(key, articleId);
        }
    }

    /**
     * 初始化当前用户的收藏记录redis数据
     * @param key 收藏key
     * @param user 用户
     */
    private void initCollectData(String key, User user) {
        Long size = redisService.zGetSize(key);
        // if set size equal 0 then initialize it
        // otherwise don't do it anything
        if (size.intValue() == 0) {
            // append a dummy element to the set anyway
            // avoid set is removed automatically when it's elements is null
            redisService.zSet(key, DEFAULT_DUMMY_ELEMENT, 0);
            List<Long> articleIds = getArticleIdsByUserId(user.getUserId());
            if (!articleIds.isEmpty()) {
                // batch insert elements to improving performance
                redisService.batchZSet(key, assemblyTuples(articleIds, 1d));
                // set the expiry time（default two hours）
                redisService.expire(key, 7200);
            }
        }
    }

    /**
     * 将数据库文章id列表装配为set<Tuple>类型 方便批量插入
     * @param articleIds 文章id列表
     * @param initScore 初始score
     * @return
     */
    private Set<TypedTuple> assemblyTuples(List<Long> articleIds, double initScore) {
        HashSet<TypedTuple> result = new HashSet<>();
        for (Long articleId : articleIds) {
            result.add(new DefaultTypedTuple(articleId, initScore++));
        }
        return result;
    }

    private List<Long> getArticleIdsByUserId(Long userId) {
        return collectionDao.getArticleIdsByUserId(userId);
    }

    /**
     * 删除收藏记录
     * @param key 收藏记录key
     * @param articleId 文章id
     */
    private void delCollectionRecord(String key, Long articleId) {
        redisService.zRemove(key, articleId);
    }

    /**
     * 增加收藏记录
     * @param key 收藏记录key
     * @param articleId 文章id
     */
    private void addCollectionRecord(String key, Long articleId) {
        redisService.zSet(
                key, articleId,
                redisService.zGetSize(key)
        );
    }

    /**
     * 根据文章id获取它所有收藏的用户id列表
     * @param id 文章id
     * @return 用户id列表
     */
    public List<Long> getUserIdsByArticleId(Long id) {
        Article article = Optional.ofNullable(articleService.selectOneById(id))
                .orElseThrow(() -> new BlogException("该文章不存在"));
        return collectionDao.findAllUserIdsByArticle(article.getArticleId());
    }

    /**
     * 将pojo转成vo类
     * @param collection 收藏对象
     * @return
     */
    public CollectionVO pojoToVO(Collection collection) {
        return CollectionVO.builder()
                .collectionId(collection.getCollectionId())
                .article(articleService.pojo2ListVO(articleService.selectOneListArticleById(collection.getArticleId())))
                .user(userService.pojoToVO(userService.selectById(collection.getUserId())))
                .build();
    }

    /**
     * 获取当前文章收藏数 每个set都有虚拟节点 所以数量要减去1
     * @param id 文章id
     * @param collectKey 文章收藏key
     * @return
     */
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

    /**
     * 获取最近size长度的收藏记录
     * @param user 用户
     * @param size 长度
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<ArticleRecordVO> recentRecord(User user, int size) {
        String key = StringUtils.generateRedisKey(USER_COLLECT_ARTICLE, user.getUserId());
        initCollectData(key, user);
        Set<TypedTuple> tuples = redisService.zGetReverseRange(key, size);
        List<Article> articles = articleService.listRecordArticleInIds(tuples
                .stream()
                .filter(t -> t.getValue() != null)
                .map(t -> Long.valueOf(t.getValue().toString()))
                .collect(Collectors.toList())
        );
        return articles.stream()
                .map(articleService::pojo2RecordVO)
                .collect(Collectors.toList());
    }
}