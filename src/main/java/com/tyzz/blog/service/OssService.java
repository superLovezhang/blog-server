package com.tyzz.blog.service;

import com.aliyuncs.auth.sts.AssumeRoleResponse;
import org.springframework.stereotype.Service;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-10 16:46
 */
@Service
public class OssService {
    public AssumeRoleResponse.Credentials getCredentials() {
        //todo 先去缓存取 取不到生成
        return null;
    }
}
