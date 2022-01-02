package com.tyzz.blog.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-01-02 10:47
 */
public enum ArticleType implements IEnum<String> {
    ORIGINAL("原创"),
    REPRINT("转载"),
    TRANSLATE("翻译");

    private String description;

    ArticleType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
