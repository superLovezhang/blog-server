package com.tyzz.blog.schedule;

import com.tyzz.blog.exception.BlogException;
import com.tyzz.blog.util.HttpClientUtils;
import com.tyzz.blog.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Description: 教育签到任务
 * Author: ZhangZhao
 * DateTime: 2022-08-24 17:41
 */
@RequiredArgsConstructor
@Component
public class EduCheckInSchedule {

    /**
     * 每天晚上1点 同步点赞数据 收藏数据
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void syncLikesData() {
        String eduCookie = HttpClientUtils.getEduCookie();
        if (StringUtils.isEmpty(eduCookie)) {
            throw new BlogException("签到cookie获取失败");
        }
        HttpClientUtils.eduCheckIn(eduCookie);
    }

}
