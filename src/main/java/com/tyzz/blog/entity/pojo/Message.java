package com.tyzz.blog.entity.pojo;

import com.tyzz.blog.enums.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Message)实体类
 * MQ发送消息的实体类
 *
 * @author ZhangZhao
 * @since 2022-05-16 13:10:16
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Message implements Serializable {
    private static final long serialVersionUID = -52483385110223386L;
    /**
     * 消息的唯一id
     */
    private Long messageId;
    /**
     * 消息内容
     */
    private String content;
    /**
     * 交换机
     */
    private String toExchange;
    /**
     * 路由键
     */
    private String routingKey;
    /**
     * 消息实体类型
     */
    private String classType;
    /**
     * 消息状态
     */
    private MessageStatus messageStatus = MessageStatus.NEW;
    /**
     * 重试次数
     */
    private Integer retryCount;
    /**
     * 失败原因
     */
    private String cause;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新事件
     */
    private Date updateTime;
    /**
     * 逻辑删除
     */
    private Boolean state;

    public void incrementRetry() {
        this.retryCount++;
    }
}