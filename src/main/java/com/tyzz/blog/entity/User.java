package com.tyzz.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import com.tyzz.blog.constant.BlogConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2021-09-26 10:51:06
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private static final long serialVersionUID = -29312194226242692L;

    @TableId
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long userId;
    
    private String username;
    
    private String avatar;
    
    private Boolean state;
    
    private String email;
    
    private String password;
    
    private Date createTime;
    
    private Date updateTime;


    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return StringUtils.isNotBlank(avatar) ? avatar : BlogConstant.USER_DEFAULT_AVATAR;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}