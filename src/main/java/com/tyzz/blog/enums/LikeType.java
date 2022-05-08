package com.tyzz.blog.enums;

import static com.tyzz.blog.constant.BlogConstant.*;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-07 17:10
 */
public enum LikeType {
    ARTICLE(0, ARTICLE_LIKE, "articleService"),
    COMMENT(1, COMMENT_LIKE, "commentService");

    private int code;
    private String type;
    private String beanName;

    LikeType(int code, String type, String beanName) {
        this.code = code;
        this.type = type;
        this.beanName = beanName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
