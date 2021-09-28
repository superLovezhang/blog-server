package com.tyzz.blog.entity.vo;

import lombok.Data;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-28 15:20
 */
@Data
public class BasePageVO {
    private Integer page = 1;
    private Integer size = 10;
}
