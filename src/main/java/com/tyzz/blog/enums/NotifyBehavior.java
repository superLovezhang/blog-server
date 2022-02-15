package com.tyzz.blog.enums;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-02-15 16:13
 */
public enum NotifyBehavior {
    PASS("通过"),
    REJECT("拒绝"),
    DELETE("删除"),
    LIKE("点赞"),
    COMMENT("评论");

    private String desc;

    NotifyBehavior(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
