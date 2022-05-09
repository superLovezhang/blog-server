package com.tyzz.blog.schedule;

import com.tyzz.blog.constant.BlogConstant;
import com.tyzz.blog.service.impl.RedisService;
import com.tyzz.blog.service.impl.UserLikeService;
import com.tyzz.blog.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Description:
 * 用来将Redis数据同步到Mysql中，解决数据不一致
 * @Author: ZhangZhao
 * DateTime: 2022-05-07 11:05
 */
@RequiredArgsConstructor
@Component
public class RedisSchedule {
    private final RedisService redisService;
    private final RedisTemplate redisTemplate;
    private final UserLikeService userLikeService;

    /**
     * todo 每天晚上12点 同步点赞数据
     */
    @Scheduled(cron = "0 0 24 * * *")
    public void syncLikesData() {
        //1.查询所有点赞set
        List<String> articleLikes = redisService.scan(BlogConstant.ARTICLE_LIKE + "*");
        List<String> commentLikes = redisService.scan(BlogConstant.COMMENT_LIKE + "*");
        //2.获取点赞事务id 去数据库查询对应列表结果
        for (String articleKey : articleLikes) {
            Set<Object> userIdsFromRedis = redisService.sGet(articleKey);
            List<Long> userIdsFromDB = new ArrayList<>();
            String[] splitArr = StringUtils.splitRedisKey(articleKey);

            if (splitArr.length == 3) {
                Long articleId = Long.parseLong(splitArr[2]);
                userIdsFromDB = userLikeService.findAllUserIdsByArticle(articleId);
            }
            //给出两个数组nums1，nums2，求出nums1中所有在nums2未出现的元素
            //eg [1,2,3,4,5] [1,2,3] result: [4,5]
            //todo 找出新建的userId数据
        }
        //3.存在修改 不存在插入
        //4.删除redis内存数据
    }

    /**
     * todo 每天晚上十二点同步收藏数据
     */
    @Scheduled(cron = "0 0 24 * * *")
    public void syncCollectsData() {

    }
}
