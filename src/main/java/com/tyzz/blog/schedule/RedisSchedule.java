package com.tyzz.blog.schedule;

import org.springframework.stereotype.Component;

/**
 * Description:
 * 用来将Redis数据同步到Mysql中，解决数据不一致
 * @Author: ZhangZhao
 * DateTime: 2022-05-07 11:05
 */
@Component
public class RedisSchedule {

    /**
     * todo 每天晚上12点 同步点赞数据
     */
//    @Scheduled(cron = "")
    public void syncLikesData() {

    }

    /**
     * todo 每天晚上十一点同步收藏数据
     */
//    @Scheduled(cron = "")
    public void syncCollectsData() {

    }
}
