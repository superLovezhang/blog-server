package com.tyzz.blog.entity.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-05-11 14:24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity implements Serializable {
    private Boolean state;

    private Date createTime;

    private Date updateTime;
}
