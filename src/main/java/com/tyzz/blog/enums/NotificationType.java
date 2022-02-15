package com.tyzz.blog.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-02-15 15:47
 */
public enum NotificationType implements IEnum<String> {
    COMMENT("评论"),
    ARTICLE("文章");

    private String desc;

    NotificationType(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
