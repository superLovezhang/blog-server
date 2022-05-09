package com.tyzz.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Description:
 * @Author: ZhangZhao
 * DateTime: 2021-10-09 10:57
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class JunitTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void test1() {
        System.out.println();
    }
}
