package com.tyzz.blog.entity.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 13:37
 */
@Data
public class UserDTO {
    @Email(message = "邮箱格式错误")
    private String email;

    @NotBlank(message = "昵称不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private String avatar;
}
