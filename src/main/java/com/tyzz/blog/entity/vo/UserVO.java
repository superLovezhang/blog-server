package com.tyzz.blog.entity.vo;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Description:
 *
 * @Author: ZhangZhao
 * DateTime: 2021-09-27 13:37
 */
@Data
public class UserVO {
    @Email(message = "邮箱格式错误")
    private String email;

    @NotEmpty(message = "昵称不能为空")
    private String username;

    @NotEmpty(message = "密码不能为空")
    private String password;

    private String avatar;
}
