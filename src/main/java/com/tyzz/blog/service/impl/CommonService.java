package com.tyzz.blog.service.impl;

import com.tyzz.blog.entity.pojo.User;
import com.tyzz.blog.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Function;

import static com.tyzz.blog.constant.BlogConstant.DEFAULT_DUMMY_ELEMENT;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-08 10:41
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class CommonService {
    private final RedisService redisService;

    /**
     * 核心类似点赞业务逻辑 减少冗余代码
     * @param id 事物id
     * @param user 用户
     * @param prefix key的前缀
     * @param obtainUserIds 获取当前事物点赞用户ids函数
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public void commonSetOperation(Long id, User user,
                           String prefix,
                           Function<Long, List<Long>> obtainUserIds) {
        String key = StringUtils.generateRedisKey(prefix, id);
        initCountData(id, obtainUserIds, key);
        boolean hasKey = redisService.sHasKey(key, user.getUserId());
        doLike(user, key, hasKey);
    }

    private void doLike(User user, String key, boolean hasKey) {
        //取消点赞
        if (hasKey) {
            redisService.setRemove(key, user.getUserId());
        } else {
            //点赞
            redisService.sSet(key, user.getUserId());
        }
    }

    public void initCountData(Long id, Function<Long, List<Long>> obtainUserIds, String key) {
        if (!redisService.hasKey(key)) {
            //如果缓存不存在 先去数据库找到数据
            List<Long> userIds = obtainUserIds.apply(id);
            //先添加一个虚拟元素 防止set集合为空 key自动删除了
            redisService.sSet(key, DEFAULT_DUMMY_ELEMENT);
            if (!userIds.isEmpty()) {
                //把数据添加缓存 再操作
                redisService.sSet(key, userIds.toArray());
            }
        }
    }
}
