package com.tyzz.blog.enums;

import com.baomidou.mybatisplus.annotation.IEnum;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-16 13:35
 */
public enum MessageStatus implements IEnum<String> {
    NEW("新建"),
    /*一个消息成功发送到Exchange并且被正确投递到Queue上*/
    SENT("发送成功"),
    /*一个消息没有发送到Exchange上或者没有Queue能够分配*/
    SENT_ERROR("发送失败");

    private final String desc;

    MessageStatus(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    @Override
    public String getValue() {
        return this.name();
    }
}
