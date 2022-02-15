package com.tyzz.blog.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-01-20 16:22
 */
public enum ArticleStatus implements IEnum<String> {
    REVIEW("审核中"),
    PASS("已通过"),
    REFUSE("已拒绝");

    private String desc;

    ArticleStatus(String desc) {
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
