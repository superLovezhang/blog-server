package com.tyzz.blog.schedule;

import com.tyzz.blog.schedule.service.redis.RedisSyncContext;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description:
 * 用来将Redis数据同步到Mysql中，解决数据不一致
 * @Author: ZhangZhao
 * DateTime: 2022-05-07 11:05
 */
@RequiredArgsConstructor
@Component
public class RedisSchedule {
    private final RedisSyncContext redisSyncContext;

    /**
     * 每天晚上12点 同步点赞数据 收藏数据
     */
    @Scheduled(cron = "0 0 24 * * *")
    public void syncLikesData() {
        //责任链+模板 解耦+减少冗余
        redisSyncContext.invoke();
    }

}
