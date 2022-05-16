package com.tyzz.blog.entity.pojo;

import java.util.Date;
import java.io.Serializable;

/**
 * (Message)实体类
 *
 * @author ZhangZhao
 * @since 2022-05-16 13:10:16
 */
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
    * 消息类型
    */
    private String messageStatus;
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


    public Long getMessageId() {
        return messageId;
    }

    public void setMessageId(Long messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToExchange() {
        return toExchange;
    }

    public void setToExchange(String toExchange) {
        this.toExchange = toExchange;
    }

    public String getRoutingKey() {
        return routingKey;
    }

    public void setRoutingKey(String routingKey) {
        this.routingKey = routingKey;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(String messageStatus) {
        this.messageStatus = messageStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

}