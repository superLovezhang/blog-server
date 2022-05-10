package com.tyzz.blog.schedule.service.redis;

import com.tyzz.blog.constant.BlogConstant;
import com.tyzz.blog.service.impl.CollectionService;
import com.tyzz.blog.service.impl.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-10 13:31
 */
@Order(3)
@Component
@RequiredArgsConstructor
public class CollectionRedisSync extends RedisSync {
    private final RedisService redisService;
    private final CollectionService collectionService;


    @Override
    protected List<String> getLikeKeys() {
        return redisService.scan(BlogConstant.ARTICLE_COLLECT + "*");
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
        return new HashSet<>(collectionService.findAllUserIdsByArticle(entityId));
    }

    @Override
    protected void likeSave(Long entityId, Long userId) {
        collectionService.save(collectionService.create(entityId, userId));
    }

    @Override
    protected void likeDel(Long entityId, Long userId) {
        collectionService.delete(entityId, userId);
    }

    @Override
    protected void afterDataSync(String key, Long entityId) {
        redisService.del(key);
    }
}
