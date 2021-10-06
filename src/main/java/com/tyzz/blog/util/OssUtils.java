package com.tyzz.blog.util;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.auth.sts.AssumeRoleRequest;
import com.aliyuncs.auth.sts.AssumeRoleResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-06 10:59
 */
public class OssUtils {

    public static AssumeRoleResponse.Credentials generateCredentials() {
        // STS接入地址，例如sts.cn-hangzhou.aliyuncs.com。
        String endpoint = "oss-cn-guangzhou.aliyuncs.com";
        // 填写步骤1生成的访问密钥AccessKey ID和AccessKey Secret。
        String AccessKeyId = "LTAI5tDTEipghFJ9zmSPevYc";
        String accessKeySecret = "R8e6RPo85QOAwuHU93hvoysJvt0adg";
        // 填写步骤3获取的角色ARN。
        String roleArn = "acs:ram::1052557574805137:role/zz-arm";
        // 自定义角色会话名称，用来区分不同的令牌，例如可填写为SessionTest。
        String roleSessionName = "sessionTest";
        String bucketName = "zz--blog";
        try {
            // regionId表示RAM的地域ID。以华东1（杭州）地域为例，regionID填写为cn-hangzhou。也可以保留默认值，默认值为空字符串（""）。
            String regionId = "cn-guangzhou";
            // 添加endpoint。
            DefaultProfile.addEndpoint(regionId, regionId, bucketName, endpoint);
            // 构造default profile。
            IClientProfile profile = DefaultProfile.getProfile("cn-shanghai", AccessKeyId, accessKeySecret);
            // 构造client。
            DefaultAcsClient client = new DefaultAcsClient(profile);
            final AssumeRoleRequest request = new AssumeRoleRequest();
            request.setMethod(MethodType.POST);
            request.setRoleArn(roleArn);
            request.setRoleSessionName(roleSessionName);
            request.setPolicy(null); // 如果policy为空，则用户将获得该角色下所有权限。
            request.setDurationSeconds(3600L); // 设置临时访问凭证的有效时间为3600秒。
            final AssumeRoleResponse response = client.getAcsResponse(request);
            System.out.println("Expiration: " + response.getCredentials().getExpiration());
            System.out.println("Access Key Id: " + response.getCredentials().getAccessKeyId());
            System.out.println("Access Key Secret: " + response.getCredentials().getAccessKeySecret());
            System.out.println("Security Token: " + response.getCredentials().getSecurityToken());
            System.out.println("RequestId: " + response.getRequestId());
            return response.getCredentials();
        } catch (ClientException e) {
            System.out.println("Failed：");
            System.out.println("Error code: " + e.getErrCode());
            System.out.println("Error message: " + e.getErrMsg());
            System.out.println("RequestId: " + e.getRequestId());
        }
        return null;
    }

}
