package com.tyzz.blog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2021-09-26 10:51:06
 */
@Data
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

    private String description;

    private Date birthday;

    private Integer gender;

    private String city;
    
    private Boolean state;
    
    private String email;
    
    private String password;
    
    private Date createTime;
    
    private Date updateTime;
}