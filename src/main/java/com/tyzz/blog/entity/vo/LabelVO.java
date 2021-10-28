package com.tyzz.blog.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-28 20:13
 */
@Data
public class LabelVO {
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long labelId;

    private String labelName;

    private Date createTime;

    private Date updateTime;
}
