package com.tyzz.blog.enums;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-13 11:09
 */
public enum MessageType {
    MESSAGE("消息"),
    INFO("服务端信息"),
    TIP("服务端提示"),
    SYNC("服务端同步记录");

    private String value;

    MessageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
