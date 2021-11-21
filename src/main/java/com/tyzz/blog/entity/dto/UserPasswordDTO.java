package com.tyzz.blog.entity.dto;

import lombok.Data;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-11-21 12:20
 */
@Data
public class UserPasswordDTO {
    private String verifyCode;
    private String password;
}
