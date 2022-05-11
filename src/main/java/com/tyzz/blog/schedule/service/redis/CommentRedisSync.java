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
 * 评论点赞同步handler
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-10 10:23
 */
@Order(2)
@Component
@RequiredArgsConstructor
public class CommentRedisSync extends RedisSync {
    private final RedisService redisService;
    private final UserLikeService userLikeService;


    @Override
    protected List<String> getLikeKeys() {
        return redisService.scan(BlogConstant.COMMENT_LIKE + "*");
    }

    @Override
    protected Set<Long> getRdbIds(String key) {
        return redisService.sGet(key)
                .stream()
                .map(i -> Long.valueOf(i.toString()))
                .collect(Collectors.toSet());
    }

    @Override
    protected Set<Long> getDbIds(Long entityId) {
        return new HashSet<>(userLikeService.findAllUserIdsByComment(entityId));
    }

    @Override
    protected void likeSave(Long entityId, Long userId) {
        userLikeService.save(userLikeService.createByCommentId(entityId, userId));
    }

    @Override
    protected void likeDel(Long entityId, Long userId) {
        userLikeService.deleteCommentLike(entityId, userId);
    }

    @Override
    protected void afterDataSync(String key, Long entityId) {
        redisService.del(key);
    }
}
