package com.tyzz.blog.schedule;

import com.tyzz.blog.util.HttpClientUtils;
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
     * 每天晚上1点签到
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void syncLikesData() {
        HttpClientUtils.eduCheckIn();
    }

}
