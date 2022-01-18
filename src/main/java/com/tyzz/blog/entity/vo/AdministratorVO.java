package com.tyzz.blog.entity.vo;

import lombok.Data;

import java.util.Date;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-01-18 15:04
 */
@Data
public class AdministratorVO {
    private Long adminId;

    private String adminName;

    private String email;

    private String avatar;

    private Date createTime;

    private Date updateTime;
}
