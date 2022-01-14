package com.tyzz.blog.entity.dto;

import com.tyzz.blog.enums.UserStatus;

/**
 * Description:
 * 管理端查询用户分页参数
 * @Author: ZhangZhao
 * DateTime: 2022-01-14 18:42
 */
public class UserAdminPageDTO extends BasePageDTO {
    private Integer gender;

    private UserStatus status;

    private String searchValue;
}
