package com.tyzz.blog.service;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.tyzz.blog.constant.BlogConstant;
import com.tyzz.blog.util.OssUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-10 16:46
 */
@Service
public class OssService {
    @Resource
    private RedisService redisService;

    public AssumeRoleResponse.Credentials getCredentials() {
        //todo 先去缓存取 取不到生成
        Object notCastValue = redisService.get(BlogConstant.OSS_CREDENTIALS_KEY);
        if (notCastValue != null) {
            return (AssumeRoleResponse.Credentials) notCastValue;
        }
        AssumeRoleResponse.Credentials credentials = OssUtils.generateCredentials();
        redisService.set(BlogConstant.OSS_CREDENTIALS_KEY, credentials);
        return credentials;
    }
}
