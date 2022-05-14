package com.tyzz.blog.entity.pojo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Description:
 * 登录记录表
 * @Author: ZhangZhao
 * DateTime: 2022-05-14 17:20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRecord {
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long loginRecordId;

    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long userId;

    private String address;

    private Date createTime;
}
