package com.tyzz.blog.schedule.service.redis;

import com.tyzz.blog.constant.BlogConstant;
import com.tyzz.blog.service.impl.RedisService;
import com.tyzz.blog.service.impl.UserLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description:
 * 文章点赞同步handler
 * @Author: ZhangZhao
 * DateTime: 2022-05-10 10:23
 */
@Order(1)
@Component
@RequiredArgsConstructor
public class ArticleRedisSync extends RedisSync {
    private final RedisService redisService;
    private final UserLikeService userLikeService;


    @Override
    protected List<String> getLikeKeys() {
        return redisService.scan(BlogConstant.ARTICLE_LIKE + "*");
    }

    @Override
    protected Set<Long> getRdbIds(String key) {
        return redisService.sGet(key)
                .stream()
                .map(i -> (Long) i)
                .collect(Collectors.toSet());
    }

    @Override
    protected Set<Long> getDbIds(Long entityId) {
        return new HashSet<>(userLikeService.findAllUserIdsByArticle(entityId));
    }

    @Override
    protected void likeSave(Long entityId, Long userId) {
        userLikeService.save(userLikeService.createByArticleId(entityId, userId));
    }

    @Override
    protected void likeDel(Long entityId, Long userId) {
        userLikeService.deleteArticleLike(entityId, userId);
    }

    @Override
    protected void afterDataSync(String key, Long entityId) {
        redisService.del(key);
    }
}
