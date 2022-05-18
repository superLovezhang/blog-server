package com.tyzz.blog.constant;

/**
 * Description:
 * 消息队列相关常量
 * @Author: ZhangZhao
 * DateTime: 2022-05-18 9:19
 */
public class MqConstant {
    public static final Integer DEFAULT_FETCH_FAILURE_COUNT = 10;
    public static final Integer DEFAULT_MAXIMUM_RETRY = 5;

    public static final String ARTICLE_NOTIFICATION_EXCHANGE = "article-notification";
    public static final String ARTICLE_AUDIT_QUEUE = "article-queue";
    public static final String ARTICLE_AUDIT_KEY = "article.audit";
}
