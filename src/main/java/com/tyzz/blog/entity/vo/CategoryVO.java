package com.tyzz.blog.entity.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.tyzz.blog.common.LongPrimaryKeySerializer;
import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-10-28 20:12
 */
@Data
public class CategoryVO {
    @JsonSerialize(using = LongPrimaryKeySerializer.class)
    private Long categoryId;

    private String categoryName;

    private String iconClass;

    private Date updateTime;

    private Date createTime;
}
