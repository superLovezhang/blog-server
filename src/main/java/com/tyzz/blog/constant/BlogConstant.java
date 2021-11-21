package com.tyzz.blog.constant;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-26 12:28
 */
public class BlogConstant {
    public final static String AUTHORIZED_URL_PATTERN = "/admin/**";
    public final static String UNAUTHORIZED_URL_PATTERN = "/open/**";
    public final static String USER_DEFAULT_AVATAR = "https://tyzzblog.oss-cn-beijing.aliyuncs.com/2021/10/21/1634823510837image.png";
    public final static String OSS_CREDENTIALS_KEY = "credentials";
    public final static String REGISTER_VERIFY_PREFIX = "REGISTER::";
    public final static String PWD_VERIFY_PREFIX = "PASSWORD::";
    public final static String EMAIL_VERIFICATION_SUBJECT = "博客注册验证码";
    public final static String EMAIL_VERIFICATION_TEXT = "您的验证码为：";
}
