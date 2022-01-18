package com.tyzz.blog.entity.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2022-01-18 16:18
 */
@Data
public class LoginDTO {
    @NotNull(message = "账号不能为空")
    private String email;

    @NotNull(message = "密码不能为空")
    private String password;
}
