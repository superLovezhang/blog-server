package com.tyzz.blog.schedule.service;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-09 21:32
 */
public class RedisScanKeys implements RedisCallback {
    private String patternStr;

    public RedisScanKeys(String patternStr) {
        this.patternStr = patternStr;
    }

    @Override
    public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
        Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions()
                .match(patternStr)
                .build());
        ArrayList<String> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(new String(cursor.next()));
        }
        return result;
    }
}
