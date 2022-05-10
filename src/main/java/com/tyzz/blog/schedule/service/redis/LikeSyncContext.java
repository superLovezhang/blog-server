package com.tyzz.blog.schedule.service.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-10 12:57
 */
@RequiredArgsConstructor
@Component
public class LikeSyncContext {
    private final List<LikeSync> chain;
    private LikeSync sync;

    @PostConstruct
    private void init() {
        if (chain == null || chain.size() == 0) {
            throw new IllegalStateException("current LikeSync chain is empty，" +
                    "please ensure your runtime environment is or not correct.");
        }
        sync = chain.get(0);
        for (int i = 1; i < chain.size(); i++) {
            chain.get(i - 1).setNext(chain.get(i));
        }
    }

    public void invoke() {
        if (sync == null) {
            throw new IllegalStateException("current don't have any LikeSync can be invoked");
        }
        sync.sync();
    }
}
