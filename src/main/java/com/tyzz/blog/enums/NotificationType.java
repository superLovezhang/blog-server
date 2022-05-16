package com.tyzz.blog.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-02-15 15:47
 */
public enum NotificationType implements IEnum<String> {
    COMMENT("评论", "点赞"),
    ARTICLE("文章", "点赞");

    private String type;
    private String action;

    NotificationType(String type, String action) {
        this.type = type;
        this.action = action;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
