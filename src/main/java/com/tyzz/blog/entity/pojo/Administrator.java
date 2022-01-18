package com.tyzz.blog.entity.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import com.tyzz.blog.constant.BlogConstant;
import com.tyzz.blog.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (Administrator)实体类
 *
 * @author makejava
 * @since 2022-01-17 10:55:29
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Administrator implements Serializable {
    private static final long serialVersionUID = -81091020708772735L;

    @TableId
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long adminId;
    
    private String adminName;
    
    private String email;
    
    private String password;
    
    private String avatar;
    
    private Boolean state;
    
    private Date createTime;

    private Date updateTime;

    public String getAvatar() {
        return StringUtils.isEmpty(avatar) ? BlogConstant.USER_DEFAULT_AVATAR : avatar;
    }
}