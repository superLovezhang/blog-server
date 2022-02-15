package com.tyzz.blog.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * (Notification)实体类
 *
 * @author makejava
 * @since 2022-02-15 15:32:24
 */
@Data
public class Notification implements Serializable {
    private static final long serialVersionUID = 866853030506013019L;

    @TableId
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long notificationId;
    
    private String notificationType;
    
    private String content;

    private Long userId;

    private Boolean read;
    
    private Boolean state;
    
    private Date updateTime;
    
    private Date createTime;
}