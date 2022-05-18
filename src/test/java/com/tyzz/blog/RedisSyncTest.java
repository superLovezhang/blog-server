package com.tyzz.blog;

import com.tyzz.blog.schedule.service.redis.RedisSyncContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-11 17:18
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RedisSyncTest {
    @Autowired
    private RedisSyncContext context;

    @Test
    public void testSync() {
        context.invoke();
    }

    public static void main(String[] args)  {
    }
}
