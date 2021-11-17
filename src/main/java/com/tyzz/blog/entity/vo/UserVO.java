package com.tyzz.blog.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 16:17
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long userId;

    private String username;

    private String avatar;

    private String email;

    private Integer gender;

    private Date birthday;

    private String description;

    private String city;

    private Date createTime;

    private Date updateTime;
}
