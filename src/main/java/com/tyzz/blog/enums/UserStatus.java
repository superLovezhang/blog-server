package com.tyzz.blog.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-01-14 18:23
 */
public enum UserStatus implements IEnum<String> {
    FROZEN("冻结"), NORMAL("正常");

    private final String message;

    UserStatus(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
